function startLogin(){
    var uiConfig = {
        //'signInSuccessUrl': 'https://careermate.herokuapp.com/member',
        'signInOptions': [
            // Leave the lines as is for the providers you want to offer your users.
            firebase.auth.FacebookAuthProvider.PROVIDER_ID
        ],
        // Terms of service url.
        'tosUrl': 'https://careermate.firebaseapp.com',
    };

    // Initialize the FirebaseUI Widget using Firebase.
    var ui = new firebaseui.auth.AuthUI(firebase.auth());
    // The start method will wait until the DOM is loaded.
    ui.start('#firebaseui-auth-container', uiConfig);
}


initApp = function() {
firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.
        var displayName = user.displayName;
        var email = user.email;
        var emailVerified = user.emailVerified;
        var photoURL = user.photoURL;
        var uid = user.uid;
        var providerData = user.providerData;
        //alert('Save to FireBase');
        var userRef = firebase.database().ref('users/' + uid);
        console.log(userRef);
        userRef.update({
            username: displayName,
            fbId: providerData[0].uid,
            firebaseId: uid                
        });

        //Show the Send to Messenger buttom
         $('#send-to-messenger-container').html('<div class="fb-send-to-messenger" messenger_app_id="887453251382684" page_id="661794463969839" data-ref="'+ user.uid +'" color="blue" size="xlarge" ></div>');
        window.fbAsyncInit = function() {
            FB.init({
            appId: "887453251382684",
            xfbml: true,
            version: "v2.6"
            });

        };

        (function(d, s, id){
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) { return; }
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk.js";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
        user.getToken().then(function(accessToken) {
            document.getElementById('sign-in-status').textContent = 'Signed in';
            document.getElementById('sign-in').textContent = 'Sign out';
            $( '#sign-in' ).click(fbSignOut);
            $( '#username' ).text(displayName);
            document.getElementById('account-details').textContent = JSON.stringify({
                displayName: displayName,
                email: email,
                emailVerified: emailVerified,
                photoURL: photoURL,
                uid: uid,
                accessToken: accessToken,
                providerData: providerData
            }, null, '  ');
            
        });
        } else {
            $('#send-to-messenger-container').html('');
            startLogin();
        }
    }, function(error) {
        console.log(error);
    });
};

function fbSignOut(){
    firebase.auth().signOut().then(function() {
        alert('Signed Out');
    }, function(error) {
        alert('Sign Out Error', error);
    });
}

window.addEventListener('load', function() {
    initApp();
});