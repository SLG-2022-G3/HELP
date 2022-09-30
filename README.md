# HELP


## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Schema](#Schema)

## Overview..
### Description
HELP is an application that will help users who need any kind of assistance, whether they are in danger or just need help. The application will allow them to send an S.O.S to a list of contacts pre-selected by the user

### App Evaluation
- **Category:** Tools/ communications
- **Mobile:** This app would be primarily developed for mobile but would perhaps be just as viable on a computer. Functionality wouldn't be limited to mobile devices, however mobile version could potentially have more features.
- **Story:** The application allows users to send SOS messages to predefined contacts. Users can provide as well their personal health information, in case they are in an emergency situation that requires going to the Hospital.
- **Market:** Any individual could choose to use this app.
- **Habit:** Users can use it every time it's necessary to send an S.O.S urgently.
- **Scope:** The application allows users to send SOS messages to predefined contacts. Then, Users can provide as well their personal health information, in case they are in an emergency situation that requires going to the Hospital.
## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [X] User can login.
- [X] User can sign up.
- [X] User can see a list of added emergency contacts or emergency services (PNH, Pompiers...)
- [X] User can add emergency contacts
- [X] User can send a predefined SOS to predefined contacts
- [X] User can share their live location anytime, but the sharing starts automatically after they press the SOS button.
- [ ] User can edit emergency contacts
- [ ] User can remove emergency contacts


**Optional Nice-to-have Stories**

- [ ] A Profile page allows the user to add and update critical personal health information (Blood type, known allergies...)
- [ ] Users can send invite links to their contacts, to start using the application.
- [ ] User can use a predefined button shortcut to send an SOS to predefined Contacts, without opening the app.
- [ ] User can see a history of sent SOS
- [ ] User receives in-app notification, when their contacts see the SOS

### 2. Screen Archetypes

* Login 
* Register - User signs up or logs into their account
    
* Contacts Screen
   * Allows user to update personal emergency contact list 
 
* Profile Screen 
   * Allows user to update personal health information.
   * Allows user to add/update predefined S.O.S Message.
   * Lets people change language, and app notification settings. (Bonus)

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home
* Contacts
* Profile


Optional:
* 
*

**Flow Navigation** (Screen to Screen)
* Forced Log-in -> Account creation if no log in is available
* Contact -> Contact list 
* Profile -> settings

## Wireframes
<img src="https://github.com/SLG-2022-G3/HELP/blob/master/Help.jpg" width=800><br>

### [BONUS] Digital Wireframes & Mockups

<img src="https://github.com/SLG-2022-G3/HELP/blob/master/HELP%20(1).jpg" width=1080>

### [BONUS] Interactive Prototype

<img src="https://github.com/SLG-2022-G3/HELP/blob/master/Prototype.gif" width=200>

## Schema

### Models

#### User


| Property          |     Type      |     Description                                                                   |
| ------------------| ------------- | ----------------------------------------------------------------------------------|
| objectID          | String        |     unique id for the user (default field)                                        |
| username          | String        |     unique username for the user (required)                                       |
| password          | String        |     password of the user  (required)                                              |
| telephone         | Number        |     phone number of the user (required)                                           |
| email             | String        |     email of the user account                                                     |
| profilePicture    | File          |     User can add a Profile Picture                                                |
| personalInfo      | String        |     User can Some personnal health information                                    |
| sosMessage        | String        |     User can predefined the SOS Message                                           |
| createdAt         | DateTime      |     date when user is created (default field)                                     |
| updatedAt         | DateTime      |     date when user info is last updated (default field)                           |

#### Contacts

| Property          |     Type      |     Description                                                                   |
| ------------------| ------------- | ----------------------------------------------------------------------------------|
| objectID          | String        |     unique id for the contact (default field)                                     |
| name              | String        |     name for the selected contact (required)                                      |
| telephone         | Number        |     phone number of the contact (required)                                        |
| relationship      | String        |     User can tell about his/her relationship with the contact                     |
| profilePicture    | File          |     User can add an optionnal profile picture for the contact                     |
| createdAt         | DateTime      |     date when contact is created (default field)                                  |
| updatedAt         | DateTime      |     date when contact info is last updated (default field)                        |

#### SOS

| Property          |     Type      |     Description                                                                   |
| ------------------| ------------- | ----------------------------------------------------------------------------------|
| objectID          | String        |     unique id for the contact (default field)                                     |
| name              | String        |     name for the selected contact (required)                                      |
| recipient         | String        |     contact that will receive the SOS (required)                                  |
| SOSBody           | String        |     The message that will be sent to the contact   (Required)                     |
| createdAt         | DateTime      |     date when SOS is sent (default field)                                         |
| updatedAt         | DateTime      |     date when SOS info is last updated (default field)                            |

### Networking

#### List of network requests by screen

* Login 
   * (Read/GET) Read existing User Information
   
* Register - User signs up or logs into their account
   * (Create/POST) Create new User
   
* SOS Screen
   * (create/POST) User can send a predefined Message very quickly and easy
   
* Contacts Screen
   * (Create/POST) User can add new Contact to to his Emergency Contact List
   * (Read/GET) User can see already selected
   * (Update/PUT) User can edit Contact's information
   * (Delete/DELETE) User can delete contact from list
   
* Profile Screen 
   * (Create/POST) User can add Health personal Information 
   * (Read/GET) Query logged in user object
   * (Update/PUT) Logged in user can update profile Information
   * (Delete/DELETE) User can Delete selected profile Information

#### [OPTIONNAL] Existing API Endpoints
