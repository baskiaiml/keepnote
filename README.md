# Angular Final Assignment - Keep Note frontend

## Objective 

The Objective of this level of Keep is to cover the following areas :  

- Components Design  
- Components Interaction  
- Named Router Outlets  
- Testing  

## Prerequisites

1. Fork this boilerplate repository  
2. Clone the boilerplate repository and cd into it  
3. Install dependencies `npm install`  
4. Run the backend `npm run serve`  which shall run on port:3000  
5. Run the frontend `npm run start` which shall run on port:4200 

## Know your server  

On running `npm run serve`, following apis would be available for your use -  
1. To authenticate user - POST - http://localhost:3000/auth/v1 - expecting data - { username, password }  
2. To check if user is authenticated - POST - http://localhost:3000/auth/v1/isAuthenticated - expecting header - { 'Authorization', `Bearer ${token}` }  
3. To get notes - GET - http://localhost:3000/api/v1/notes - expecting header - { 'Authorization', `Bearer ${token}` }  
4. To add a note - POST - http://localhost:3000/api/v1/notes - expecting header - { 'Authorization', `Bearer ${token}` } and data - { note } 


## Assignment:	
	
Keep to include following -  
1. Notes View - collection of notes  
2. List View - collection of notes but classified into three lists based on their `state` values  
	- Not Started  
	- Started  
	- Completed  
3. Edit Note View - Both the NotesView and the ListView should be able to edit notes and should be able to update the list and notes view.  
4. Note Taker Component: Both the NotesView and the ListView should have a common NoteTakerComponent present in the DashboardComponent.  
5. Add more Unit test cases for all the Components.  
6. Add Unit test cases for all the Services.  
7. Add more E2E test cases to the application.    

## Instructions:  

1. You are expected to use `Note` class for Note model  
2. `AppModule` shall be the root module  
3. Application to have following routes -  
	3.1. `dashboard` mapped to `DashboardComponent`  
	3.2. `dashboard/view/noteview` mapped to `NoteViewComponent`  
	3.3. `dashboard/view/listview` mapped to `ListViewComponent`  
	3.4. `login` mapped to `LoginComponent`  
	3.5. `note/:noteId/edit` mapped to `EditNoteOpenerComponent` rendered in `noteEditOutlet` named router outlet  
	`dashboard/view/noteview` being the default route  
4. `AppComponent` as the bootstrapping component  
5. `HeaderComponent` to have a property `isNoteView` which is set based on the route rendered and two icons as shown in the template to switch between List View and Note View.  
6. `LoginComponent` same as implemented in `Keep Level 2`  
7. `DashboardComponent` calls notesService.fetchNotesFromServer() on initialization and renders components based on router outlets mentioned in the template  
8. `NoteTakerComponent` has the same implementation of Expansion panel to add new notes from `DashboardComponent` of `Keep Level 2`  
9. `NoteViewComponent` displays the notes collection with each note rendered as a separate NoteComponent, the component class subscribes to notesService.getNotes() and populates the response in its `notes` property  
10. `NoteComponent` displays a single note as a Material Card, with card title set to note title and card content set to note text. On click of this card, user is navigated to Edit View of the same note.  
11. `ListViewComponent` to have three properties in the component class as - `notStartedNotes`, `startedNotes`, `completedNotes` into which the updated collection of notes is classified based on the `state` property of Note objects.  
12. `EditNoteOpenerComponent` which gets rendered when application is routed to Edit View. This component extracts noteId from query parameter and passed same as data while opening EditNoteViewComponent in a Material Dialog.  
13. `EditNoteViewComponent` enables editing of a note by fetching the entire note using notesService.getNoteById() based on noteId provided in the data while opening this component in a Material Dialog. `onSave()` persists the updated note via notesService.editNote() and the dialog is closed after which the previous route is rendered by calling routerService.routeBack(). In case of any server error, the template has a label with class `error-message`, the text of which is set with the error object's message property.  
14. `CanActivateRouteGuard` same as implemented in `Keep Level 2`  
15. `AuthenticationService` same as implemented in `Keep Level 2`  
16. `NotesService` with two properties -> `notes` which is array of all the updated notes and `notesSubject` which is a BehaviourSubject of array of all updated notes and helps to emit changes to this array across subscribers, the service has methods as below -  
	16.1. `fetchNotesFromServer()` to fetch notes from backend and update the `notes` and `notesSubject` likewise in the service  
		INPUT - no params  
		URL - `http://localhost:3000/api/v1/notes`  
		METHOD - `GET`  
		RETURN TYPE - void  
	16.2. `getNotes()` to return the `notesSubject` of the service  
		INPUT - no params  
		RETURN TYPE - `BehaviorSubject<Array<Note>>`  
	16.3. `addNote()` to persist the new note created with server and update the `notes` and `notesSubject` of the service  
		INPUT - Note  
		URL - `http://localhost:3000/api/v1/notes`  
		METHOD - `POST`  
		RETURN TYPE - `Observable<Note>`  
	16.4. `editNote()` to persist the updated note with server and update the `notes` and `notesSubject` of the service  
		INPUT - Note  
		URL - `http://localhost:3000/api/v1/notes/${note.id}`  
		METHOD - `PUT`  
		RETURN TYPE - `Observable<Note>`  
	16.5. `getNoteById()` to fetch the note matching provided Id from `notes` collection of the service  
		INPUT - Number (noteId)  
		RETURN TYPE - Note  
17. `RouterService` to navigate user to available routes -  
	17.1. `routeToDashboard()` to navigate to dashboard route  
	17.2. `routeToLogin()` to navigate to login route  
	17.3. `routeToEditNoteView()` to navigate to edit view of the note of given noteId  
	17.4. `routeBack()` to navigate back to previous route  
	17.5. `routeToNoteView()` to navigate to notes view  
	17.6. `routeToListView()` to navigate to list view  
18. Ensure following commands succeed in your local machine before submitting your code for Preliminary automated review as described next -  
`npm install
npm run build
npm run lint
`
19. Ensure unit test cases pass -  
`npm run test
`
20. Ensure e2e test cases pass -  
`npm run serve` (backend shall be running before executing e2e test cases)  
`npm run e2e`

## Submitting your solution for preliminary automated review  
1. Open `https://hobbes-cts.stackroute.in/#/` and login into the platform  
2. Under `Assignment repository` select `angular-keep-level-3-assignment`, and branch `master`  
3. Under `Your solution repository` select your own repository and branch
4. Press `Submit`  
5. Press `click here` for the feedback  
6. Evaluation will take around 5 mins to complete after which you need to refresh your browser and get the updated status  
7. Watch out for your total score and detailed status on each test and eslint errors in the coloured blocks on the screen  
8. Fix failing test cases as well as eslint errors and re-submit your solution (you may skip any eslint errors reported in the provided spec files)  


## MENTORS TO BEGIN REVIEW YOUR WORK ONLY AFTER ->

- You add the respective Mentor as a Reporter/Master into your Assignment Repository

- You have checked your Assignment on the Automated Evaluation Tool - Hobbes (Check for necessary steps in your Boilerplate - README.md file. ) and got the required score - Check with your mentor about the Score you must achieve before it is accepted for Manual Submission.

- Intimate your Mentor on Slack and/or Send an Email to learner.support@stackroute.in - with your Git URL - Once you are done working and are ready for final submission.




## KeepNote Backend

### Assignment Step Description

In this case study Keep Note Step 6, we will implement JWT (JSON Web Token) on top of Keep Note Step 5 Assignment. JSON Web Token (JWT) is an open standard (RFC 7519) that 
defines a compact and self-contained way for securely transmitting information between parties as a JSON object. This information can be verified and trusted because it is
digitally signed.

In this step, we will create this application in five parts 
    
        1. AuthenticationService
        2. UserService
        3. NoteService
        4. CategoryService
        5. ReminderService

### Steps to be followed:

    Step 1: Clone the boilerplate in a specific folder on your local machine and import the same in your eclipse STS.
    Step 2: Go thru the readme.md file and implement the code for AuthenticationService and run the test cases.
    Step 3: Go thru the readme.md file and implement the code for UserService and run the test cases.
    Step 4: Go thru the readme.md file and implement the code for NoteService  and run the test cases.
    Step 5: Go thru the readme.md file and implement the code for CategoryService and run the test cases.
    Step 6: Go thru the readme.md file and implement the code for ReminderService and run the test cases.

### Project structure

The folders and files you see in this repositories, is how it is expected to be in projects, which are submitted for automated evaluation by Hobbes

    Project
	|
	├── AuthenticationService                   // This is the microservice for User Authentication
	├── NoteService                             // This is the microservice of Note   
	├── CategoryService                         // This is the microservice of Category   
	├── ReminderService                         // This is the microservice of Reminder   
	├── UserService                             // This is the microservice of User   
	├── .gitignore			                    // This file contains a list of file name that are supposed to be ignored by git 
	├── .hobbes   			                    // Hobbes specific config options, such as type of evaluation schema, type of tech stack etc., Have saved a default values for convenience
	├── .project			                    // This is automatically generated by eclipse, if this file is removed your eclipse will not recognize this as your eclipse project. 
	└── pom.xml 			                    // This is the parent POM, which holds all the microservice projects.

> PS: All lint rule files are by default copied during the evaluation process, however if need to be customizing, you should copy from this repo and modify in your project repo


#### To use this as a boilerplate for your new project, you can follow these steps

1. Clone the base boilerplate in the folder **assignment-solution-step6** of your local machine
     
    `git clone https://gitlab-cts.stackroute.in/stack_java_keep/KeepNote-Step6-Boilerplate.git assignment-solution-step6`

2. Navigate to assignment-solution-step6 folder

    `cd assignment-solution-step6`

3. Remove its remote or original reference

     `git remote rm origin`

4. Create a new repo in gitlab named `assignment-solution-step6` as private repo

5. Add your new repository reference as remote

     `git remote add origin https://gitlab-dev.stackroute.in/{{yourusername}}/assignment-solution-step6`

     **Note: {{yourusername}} should be replaced by your username from gitlab**

5. Check the status of your repo 
     
     `git status`

6. Use the following command to update the index using the current content found in the working tree, to prepare the content staged for the next commit.

     `git add .`
 
7. Commit and Push the project to git

     `git commit -a -m "Initial commit | or place your comments according to your need"`

     `git push -u origin master`

8. Check on the git repo online, if the files have been pushed

### Important instructions for Participants
> - We expect you to write the assignment on your own by following through the guidelines, learning plan, and the practice exercises
> - The code must not be plagirized, the mentors will randomly pick the submissions and may ask you to explain the solution
> - The code must be properly indented, code structure maintained as per the boilerplate and properly commented
> - Follow through the problem statement shared with you

### MENTORS TO BEGIN REVIEW YOUR WORK ONLY AFTER ->
> - You add the respective Mentor as a Reporter/Master into your Assignment Repository
> - You have checked your Assignment on the Automated Evaluation Tool - Hobbes (Check for necessary steps in your Boilerplate - README.md file. ) and got the required score - Check with your mentor about the Score you must achieve before it is accepted for Manual Submission.
> - Intimate your Mentor on Slack and/or Send an Email to learner.support@stackroute.in - with your Git URL - Once you done working and is ready for final submission.


### Further Instructions on Release

*** Release 0.1.0 ***

- Right click on the Assignment select Run As -> spring boot app to run your Assignment.
- Right click on the Assignment select Run As -> JUnit Test to run your Assignment.
