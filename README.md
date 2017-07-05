# Map Chat

## Overview
A location based chat application. Login and hold the map to drop a message and see messages from other users instanstly, anywhere.

## Login
Click Sign-in on the main login screen to begin the sign in process. The app uses SSO available with Google, Facebook, or Twitter. Select the method you would like to sign in, and if the credentials exist the application will start. The application only uses basic profile information from each source, namely a display name and photo if available.

## Main Screen
Upon loading the main screen the application will request location permissions. If the permissions are granted, the application will center on the users current location, otherwise it will move to the default location.

Logging in will immediately notify other users by dropping a small round layover of your profile image near your location. If you choose not to provide your location then other users will not be notified of your logging in or out. You will also see small round layovers of other users who log into the application in the area you are viewing, which will then be removed if they log out. The layovers move with the users.

Messages are added by clicking and holding anywhere on the map, which will bring up a dialog for you to enter your message. Messages can be multi-line but have a 140-Character limit. Messages are displayed as small squares on the map containing either the creating user's profile image, or the default image of an envelope. Click any of these squares to display the message contents. To delete a message, long click on the message contents (the text itself). You are not able to delete another user's message.

Messages created by you or other users are immediately available to all users currently connected to and using the application.

If the user has elected to provide their location, the top right of the map shows the my location button, that can be used to return the vieing area to the user's actual location at any time.

## Settings Drawer
By clicking the top left application icon in the app bar, or swiping from the left side of the screen, you can display the settings drawer.

### Go to Location
Uses the Google places API to search for a location and once selected, immediately move your viewing area to that location, allowing you to quickly drop messages anywhere in the world.

### Remove My Messages
Allows you to instantly remove all messages that you have dropped.

### Map Theme
Allows you to select from several different styles of map to be used in the main window. Only one is available with labels as I found that labels easily obstructed the messages.

### Sign out
Sign out the application and return to the login screen. Removes your circular photo overlay from the view of other users, however does not remove your messages.

### Delete Account
Signs out, deletes account from server, and removes any dropped messages.

