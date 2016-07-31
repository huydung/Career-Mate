

//Configure the Express app
const APPDOMAIN = 'https://careermate.herokuapp.com'
const express = require('express')
const bodyParser = require('body-parser')
const request = require('request')
const app = express()
// set the view engine to ejs
app.set('view engine', 'ejs')
// Process application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({extended: false}))

// Process application/json
app.use(bodyParser.json())

app.set('port', (process.env.PORT || 5000))
app.use(express.static('public'));

//Messenger token
const token = "CENSORED";

const congratulations = [
  "Awesome!", "Good job!", "That's it!", "Well Done", "You got it!"
];

function getCongratText(){
  var randomId = Math.floor( Math.random() * congratulations.length );
  return congratulations[randomId];
}

//Configure the FireBase app
const firebase = require('firebase')
firebase.initializeApp({
  serviceAccount: {
    "info": "CENSORED",    
    },
  databaseURL: "https://careermate.firebaseio.com"
});
var db = firebase.database();

function onFireBaseError(err){ console.log(err); }


// Index route
app.get('/', function (req, res) {
    res.render('home')
})

// Index route
app.get('/member', function (req, res) {
    res.render('member')
})

// for Facebook verification
app.get('/webhook/', function (req, res) {
    if (req.query['hub.verify_token'] === 'CENSORED') {
        res.send(req.query['hub.challenge'])
    }
    res.send('Error, wrong token')
})

app.post('/webhook/', function (req, res) {
    var data = req.body;

    // Make sure this is a page subscription
    if (data.object == 'page') {
        // Iterate over each entry
        // There may be multiple if batched
        data.entry.forEach(function(pageEntry) {
        var pageID = pageEntry.id;
        var timeOfEvent = pageEntry.time;

        // Iterate over each messaging event
        pageEntry.messaging.forEach(function(messagingEvent) {
            if (messagingEvent.optin) {
                receivedAuthentication(messagingEvent);
            } else if (messagingEvent.message) {
                receivedMessage(messagingEvent);
            } else if (messagingEvent.delivery) {
                //receivedDeliveryConfirmation(messagingEvent);
            } else if (messagingEvent.postback) {
                receivedPostback(messagingEvent);
            } else {
                console.log("Webhook received unknown messagingEvent: ", messagingEvent);
            }
        });
        });

        res.sendStatus(200);
    }
})

function receivedMessage(event) {
  var senderID = event.sender.id;
  var recipientID = event.recipient.id;
  var timeOfMessage = event.timestamp;
  var message = event.message;

  console.log("Received message for user %d and page %d at %d with message:", 
    senderID, recipientID, timeOfMessage);
  console.log(JSON.stringify(message));

  var messageId = message.mid;

  var messageText = message.text;
  var messageAttachments = message.attachments;
  
  //Whatever the message,we'll most likely need the user info
  //First get the lesson info from the users 
  db.ref('users')
    .orderByChild('messengerId')
    .equalTo(senderID)
    .limitToFirst(1)
    .once('value', function (user) {
      var userObj = user.val();
      if( userObj ){
        var firebaseId =  Object.keys(userObj)[0]; // safe, because three's only 1 property
        userObj = userObj[firebaseId];
        console.log(userObj);

        if (messageText) {

          if( userObj.expected_answer ){
            console.log("Expecting answer: " + userObj.expected_answer);
            if( compareAnswer(messageText, userObj.expected_answer) ){
              sendTextMessage(senderID, getCongratText());
              db.ref('users/'+firebaseId).update({"expected_answer": null});
              setTimeout(function(){
                startSession(senderID, userObj);
              }, 100);
              
            } else {
              sendTextMessage(senderID, "Not a correct answer for last question :) Pls try again.");
              //startSession(senderID, userObj);
            }            
          } else {
            switch (messageText) {
                case 'OK': {
                  if( message.quick_reply && message.quick_reply.payload == 'OK START LEARNING' ) {
                    sendTextMessage(senderID, "Great!");
                    setTimeout(function(){
                      startSession(senderID, userObj);
                    }, 100);                    
                  }
                  break;              
                }
                case 'Done': {
                  if( message.quick_reply && message.quick_reply.payload == 'OK CONTINUE' ) {
                    startSession(senderID, userObj);
                  }
                  break;              
                }
                case 'Not Now': {
                  if( message.quick_reply && message.quick_reply.payload == 'NOT NOW' ) {
                    sendTextMessage(senderID, "Uhm okay... See you later.");                
                  }    
                  break;          
                }   
                default:
                  //sendTextMessage(senderID, messageText);
                  var activeCourse = findCourseWithStatus(userObj, 'Active');
                  if( activeCourse ) {
                    suggestStartLearning(senderID);
                  } else {
                    listCourses(senderID, userObj);
                  }
                  
              }     
            
          } 
        } else if (messageAttachments) {
              sendTextMessage(senderID, "Nice one, lah!");
        }         

        
      }
      
    }, onFireBaseError);  
}

function findCourseWithStatus(userObj, statusValue){
  var course = false;
  if( userObj.courses ){
    Object.keys(userObj.courses).forEach(function(key, index, keys){
      //console.log(userObj.courses[key]['status']);
      if( userObj.courses[key]['status'] === statusValue ){    
        course = userObj.courses[key];
      };
    });
  }  
  return course;
}

function sendTextMessage(recipientId, messageText) {
  var messageData = {
    recipient: { id: recipientId },
    message: {
      text: messageText
    }
  };

  callSendAPI(messageData);
}

function sendVideoCard(recipientId, cardInfo){

    showLoading(recipientId);

    console.log("sendVideoCard");
    if( cardInfo.type === "video" ){
      var messageData = {
        recipient: {id: recipientId},
        message: {
          attachment: {
            type: "video",
            payload: {
              url: APPDOMAIN + cardInfo.source_url
            }
          },
          "quick_replies":[
            {
              "content_type":"text",
              "title":"Done",
              "payload":"OK CONTINUE"
            },
            {
              "content_type":"text",
              "title":"Not Now",
              "payload":"NOT NOW"
            }
          ]
        }
      };

      callSendAPI(messageData);
    }
}

function compareAnswer(txt1, txt2){
  return txt1.trim().toLowerCase() == txt2.trim().toLowerCase();
}

function suggestStartLearning(recipientId){
  var messageData = {
    "recipient": { "id": recipientId },
    "message": {
      "text": "Hi there! Shall we start the session today?",
      "quick_replies":[
        {
          "content_type":"text",
          "title":"OK",
          "payload":"OK START LEARNING"
        },
        {
          "content_type":"text",
          "title":"Not Now",
          "payload":"NOT NOW"
        }
      ]
    }
  };
  callSendAPI(messageData);
}

function sendQuizButtonForText(recipientId, cardInfo){

  var buttons = [];
  Object.keys(cardInfo.options).forEach(function(key,index) {
      buttons.push({
        "type": "postback",
        "title": cardInfo.options[key],
        "payload": compareAnswer(cardInfo.options[key], cardInfo.answer) ? "correct" : "incorrect" 
      })
  });
  console.log(buttons);
  //if( cardInfo.type === "quiz" ){

  var messageData = {
    recipient: { id: recipientId },
    "message": {
      "attachment": {
        "type": "template",
        "payload": {
          "template_type": "generic",
          "elements":[
            {
              "title": cardInfo.content,
              "buttons": buttons
            }	          
          ]          
        }
      }
    }
  };
  callSendAPI(messageData);
  //}
}

function sendQuizButtonForGeneric(recipientId, cardInfo){
  //showLoading(recipientId);

  var buttons = [];
  Object.keys(cardInfo.options).forEach(function(key,index) {
      buttons.push({
        "type": "postback",
        "title": cardInfo.options[key],
        "payload": compareAnswer(cardInfo.options[key], cardInfo.answer) ? "correct" : "incorrect" 
      })
  });
  console.log(buttons);
  //if( cardInfo.type === "quiz" ){
  var messageData = {
    recipient: { id: recipientId },
    "message": {
      "attachment": {
        "type": "template",
        "payload": {
          "template_type": "generic",
          "elements":[
            {
              "title": cardInfo.content,
              "image_url": APPDOMAIN + cardInfo.source_url,
              "buttons": buttons
            }	          
          ]          
        }
      }
    }
  };

  callSendAPI(messageData);
  //}
}

function callSendAPI(messageData) {
  console.log(messageData);
  request({
    uri: 'https://graph.facebook.com/v2.6/me/messages',
    qs: { access_token: token },
    method: 'POST',
    json: messageData

  }, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      var recipientId = body.recipient_id;
      var messageId = body.message_id;

      console.log("Successfully sent generic message with id %s to recipient %s", 
        messageId, recipientId);
    } else {
      console.error("Unable to send message.");
      //console.error(response);
      console.error(error);
    }
  });  
}

function receivedPostback(event) {
  var senderID = event.sender.id;
  var recipientID = event.recipient.id;
  var timeOfPostback = event.timestamp;

  // The 'payload' param is a developer-defined field which is set in a postback 
  // button for Structured Messages. 
  var payload = event.postback.payload;

  //Whatever the message, then we save this in the user data on FireBase
  //First get the lesson info from the users 
  db.ref('users')
    .orderByChild('messengerId')
    .equalTo(senderID)
    .limitToFirst(1)
    .once('value', function (user) {
      var userObj = user.val();
      if(userObj) {
        var firebaseId =  Object.keys(userObj)[0];//safe, because there's only 1 key
        userObj = userObj[firebaseId];
        console.log(userObj);

        var userRef = db.ref('users/' + userObj.firebaseId);
        var activeCourse = findCourseWithStatus(userObj, 'Active');
        var lesson = activeCourse.next_lesson;
        var card = activeCourse.next_card;

        // When a postback is called, we'll send a message back to the sender to 
        // let them know it was successful
        if( payload === "correct" ){
          sendTextMessage(senderID, getCongratText());
          db.ref('users/'+firebaseId).update({"expected_answer": null});
          setTimeout(function(){
            startSession(senderID, userObj);
          }, 500);
          //Show Next Card
        } else if ( payload === "incorrect" ) {
          sendTextMessage(senderID, "Uhm, not that one. Pls try again.");
        } else if ( payload.indexOf("Chose Course:") == 0 ) {
          //save active course to Fire Base
          console.log("Will save active Course to user profile");
          var courseKey = payload.replace("Chose Course:", "");
          db.ref('users/'+firebaseId).update({'courses': null});
          db.ref('users/'+firebaseId+'/courses/'+courseKey).update({
            status:"Active",
            key: courseKey,
            name: courseKey, //cheat
            next_card: "0", //cheat
            next_lesson: "0" //cheat
          });
          console.log("Saved active Course to user profile");
          suggestStartLearning(senderID);
        } else {
          sendTextMessage(senderID, "Received Payload");
        }

      }
      
    }, onFireBaseError);  

  
}

function listCourses(recipientId, userObj){
  db.ref('courses').once('value', function(coursesObj){
    var courses = coursesObj.val();
    console.log(courses);
    var elements = [];
    var coursesKey = Object.keys(courses).forEach(function(key, index){
      var course = courses[key];
      elements.push({
        title: course.name,
        image_url: APPDOMAIN + course.thumbnail,
        buttons: [
          {
            type: "postback",
            title: "Learn this",
            payload: "Chose Course:"+course.key
          }          
        ]        
      });
    });
    

    var messageData = {
      recipient: { id: recipientId },
      "message": {
        "attachment": {
          "type": "template",
          "payload": {
            "template_type": "generic",
            "elements":elements          
          }
        }
      }
    };

    callSendAPI(messageData);

  }, onFireBaseError);
}

function processAndSendCard(senderId, card, userObj){
  console.log("processAndSendCard");
  if( card.type === 'video' ){
    sendVideoCard(senderId, card);
  } else if( card.type === 'quiz' ) {
    if( card.presentation === 'buttons' ) {
      sendQuizButtonForText(senderId, card);
    } else if( card.presentation === 'generic' ){
      sendQuizButtonForGeneric(senderId, card);
    } else if( card.presentation === 'freetext' ){
      sendTextMessage(senderId, card.content);
    }
    db.ref('users/'+userObj.firebaseId).update({"expected_answer": card.answer});
  } else if( card.type === 'text' ) {
    var messageData = {
        recipient: {id: senderId},
        message: {
          text: card.content,
          "quick_replies":[
            {
              "content_type":"text",
              "title":"Done",
              "payload":"OK CONTINUE"
            }
          ]
        }
      };

      callSendAPI(messageData);
  }
}

function simpleSort(a, b){
  return a > b;
}

function startSession(recipientId, userObj){

      if( userObj ){

        
        var activeCourse = findCourseWithStatus(userObj, 'Active');
        if( activeCourse.status ) {

        
          var lesson = activeCourse.next_lesson;
          var card = activeCourse.next_card;
          console.log("start/continue Session");
          //Now get the course Info
          db.ref('courses') //+'/lessons/'+lesson+'/cards')
          .orderByKey()
          .equalTo(activeCourse.key)
          .limitToFirst(1)
          .once('value', function(course){
            var courseObj = course.val()[activeCourse.key];
            console.log(courseObj);
            if(courseObj) {
              //Check if we're going to finish the lesson in next card
              var lessonObj = courseObj.lessons[lesson];
              var cardKeys = Object.keys(lessonObj.cards).sort(simpleSort); //need to sort the keys to ensure order
              
              if( card == cardKeys[ cardKeys.length -1 ] ) {
                //We're going to finish the lesson in next callback   
                var lessonKeys = Object.keys(courseObj.lessons).sort(simpleSort);
                console.log(lessonKeys, lesson);
                if( lesson == lessonKeys[ lessonKeys.length - 1 ] ){
                  //We finish the course!
                  console.log('Need to update the user object');
                  db.ref('users/'+userObj.firebaseId).update({"milestone": userObj.username + ", you have amazingly finished the course '"+ courseObj.name + "' on Career Mate!"});
                  db.ref('users/' + userObj.firebaseId + '/courses/' + activeCourse.key).update({status: "Finished"});

                } else {
                  console.log('Need to update the user object to increase the lesson');
                  var index = lessonKeys.indexOf(lesson);
                  var newLesson = lessonKeys[index+1];
                  var newCardKeys = Object.keys(courseObj.lessons[newLesson].cards).sort(simpleSort);
                  var newCard = newCardKeys[0];
                  console.log({next_card: newCard, next_lesson: newLesson});
                  db.ref('users/' + userObj.firebaseId + '/courses/' + activeCourse.key).update({next_card: newCard, next_lesson: newLesson});  

                  db.ref('users/'+userObj.firebaseId).update({"milestone": userObj.username + ", you have successfully finished the lesson '"+ lessonObj.name + "' on Career Mate!"}); 

                }

              } else {
                //Simply increase the next_card index and update it to FireBase
                var index = cardKeys.indexOf(card);
                var newCard = cardKeys[index+1];
                console.log('Simply increase the card from ' + card + ' to ' + newCard);
                db.ref('users/' + userObj.firebaseId + '/courses/' + activeCourse.key).update({next_card: newCard});              
              }
              //If this is the first card, say the intro to the lesson
              if( card == cardKeys[0] ){
                if( userObj.milestone ) {
                  sendTextMessage(recipientId, "CONGRATS!!! \u1F60E " + userObj.milestone);
                  db.ref('users/' + userObj.firebaseId).update({"milestone": null});
                }
                setTimeout(function(){
                  sendTextMessage(recipientId, "Let's learn about " + lessonObj.name);
                }, 100);
              }
              var cardObj = lessonObj.cards[card];
              console.log(cardObj);
              setTimeout(function(){
                  processAndSendCard(recipientId, cardObj, userObj);
              }, 100);
              
            }         
          }, onFireBaseError);
        } //If Active Course Exist
        else {
          if( userObj.milestone ) {
            sendTextMessage(recipientId, "CONGRATS!!! \u1F60E " + userObj.milestone);
            db.ref('users/' + userObj.firebaseId).update({"milestone": null});
          }
          setTimeout(function(){
            sendTextMessage(recipientId, "Oops, looks like you have no Active Course. Let's start learning one!");
            listCourses(recipientId, userObj);
          }, 100);
          
        }
      }
}

function showLoading(recipientId){
  /*
  var messageData = {
    "recipient": { "id": recipientId },
    "sender_action":"typing_on"
  };

  callSendAPI(recipientId, messageData);
  */
}

function receivedAuthentication(messagingEvent){
    var messengerId = messagingEvent.sender.id;
    var firebaseId = messagingEvent.optin.ref;
    var ref = db.ref("users/" + firebaseId);
    ref.update({
      'messengerId': messengerId
    });
    console.log(messagingEvent);
}



// Spin up the server
app.listen(app.get('port'), function() {
    console.log('running on port', app.get('port'))
})