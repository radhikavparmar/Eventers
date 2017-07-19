# Eventers
FUNCTIONALITY:

Displays the contacts without duplicate numbers and only mobile numbers . Example, Suppose a contact Edwin is having five numbers out of which two are same which can be 09123456700 and +919123456700. Consider them as same number only. His other numbers are 0205601234, which is not a mobile number. So only numbers which are mobile and non-duplicates are displayed.

Uses library libphonenumber (com.googlecode.libphonenumber:libphonenumber:8.4.0), cardview, recycler view.

State is maintained. If a user selects the contact and comes again to the app, he/she can still change the selection. If a new contact is added to the contact app, there is a functionality to detect it when the user is selecting the contact from the list.



SCREENS:

Screen 1 - on click of button, move to screen 2 and show contacts. 

Screen 2(a) - Display contact list and option to select multiple contacts.

Screen 2(b) - In case any contact is having multiple numbers, this screen will have option to select one of the multiple numbers.

Screen 3 - Display number of selected contacts and total contacts.

![alt tag](https://user-images.githubusercontent.com/3963797/28071192-d898d6d8-666c-11e7-8724-701447e0c083.png)


![alt tag](https://user-images.githubusercontent.com/3963797/28071697-4e7e1f60-666e-11e7-9ffa-9eecccbc11fc.png)


![alt tag](https://user-images.githubusercontent.com/3963797/28071701-508c92be-666e-11e7-9d21-a5d442554eaf.png)
