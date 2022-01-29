import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule , ReactiveFormsModule } from '@angular/forms';
import { NotesService } from './services/notes.service';
import { ReminderService } from './services/reminder.service';
import { UserService } from './services/user.service';
import { AlertService } from './services/alert.service';
import { HttpClientModule,HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule , Routes } from '@angular/router';
import { AuthenticationService } from './services/authentication.service';
import { RouterService } from './services/router.service';
import { CanActivateRouteGuard } from './can-activate-route.guard';
import {AuthGuardService} from './helpers/auth.guard.service';
import {JwtInterceptor} from './helpers/jwt.interceptor.service';
import {LoaderInterceptorService} from './helpers/loader.interceptor';
import {ErrorInterceptor} from './helpers/error.interceptor.service';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';

// ------- Material imports---------
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatExpansionModule} from '@angular/material/expansion';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule} from '@angular/material/card';
import { MatOptionModule, 
         MatMenuModule,
         MatListModule,
         MatIconModule,
         MatAutocompleteModule,
           MatChipsModule,
           MatSidenavModule,
           MatProgressSpinnerModule,
           MatSnackBarModule
          } from '@angular/material';
import { MatSelectModule} from '@angular/material/select';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';
import { MatDatepickerModule,MatNativeDateModule } from '@angular/material';
import {MatGridListModule} from '@angular/material/grid-list';

import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NoteTakerComponent } from './note-taker/note-taker.component';
import { NoteViewComponent } from './note-view/note-view.component';
import { NoteComponent } from './note/note.component';
import { ListViewComponent } from './list-view/list-view.component';
import { EditNoteViewComponent } from './edit-note-view/edit-note-view.component';
import { EditNoteOpenerComponent } from './edit-note-opener/edit-note-opener.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ReminderComponent } from './reminder/reminder.component';
import { ReminderViewComponent } from './reminder-view/reminder-view.component';
import { CategoryViewComponent } from './category-view/category-view.component';
import { CategoryComponent } from './category/category.component';
import { CategoryService } from './services/category.service';
import { NoteDeleteComponent } from './note-delete/note-delete.component';
import { CategoryEditComponent } from './category-edit/category-edit.component';
import { ReminderEditComponent } from './reminder-edit/reminder-edit.component';
import { CategorySelectComponent } from './category-select/category-select.component';
import { LoaderComponent } from './loader/loader.component';
import { RegisterComponent } from './register/register.component';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { SearchResultComponent } from './search-result/search-result.component';

const appRoutes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: '',redirectTo:'login', pathMatch:'full'
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuardService],
    children: [
      {
        path: 'view/noteview',
        component: NoteViewComponent
      },
      {
        path: 'view/listview',
        component: ListViewComponent
      },
      {
        path: '',
        redirectTo: 'view/noteview',
        pathMatch: 'full'
      },
      {
        path: 'note/:noteId/edit',
        component: EditNoteOpenerComponent,
        outlet: 'noteEditOutlet'
      },
      {
        path: 'view/reminderview',
        component: ReminderComponent
      },
      {
        path: 'view/remindereditview',
        component: ReminderEditComponent
      }
      ,
      {
        path: 'view/categoryEdit', 
        component: CategoryEditComponent 
      }
    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    DashboardComponent,
    NoteTakerComponent,
    NoteViewComponent,
    NoteComponent,
    ListViewComponent,
    EditNoteViewComponent,
    EditNoteOpenerComponent,
    ReminderComponent,
    ReminderViewComponent,
    CategoryViewComponent,
    CategoryComponent,
    NoteDeleteComponent,
    CategoryEditComponent,
    ReminderEditComponent,
    CategorySelectComponent,
    LoaderComponent,
    RegisterComponent,
    SearchBarComponent,
    SearchResultComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatInputModule,
    MatFormFieldModule,
    MatExpansionModule,
    MatButtonModule,
    MatCardModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxMaterialTimepickerModule.forRoot(),
    MatDatepickerModule,
    MatNativeDateModule,
    MatAutocompleteModule,
    RouterModule.forRoot(appRoutes),
    MatDialogModule,
    MatOptionModule,
    MatSelectModule,
    MatMenuModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatChipsModule,
    MatSidenavModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  providers: [
    NotesService,
    AuthenticationService,
    RouterService,
    CategoryService,
    ReminderService,
    CanActivateRouteGuard,
    MatDatepickerModule,
    UserService,
    AlertService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    {
      provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptorService,multi: true },
  ],
  bootstrap: [AppComponent],
  entryComponents : [EditNoteViewComponent,CategorySelectComponent,CategoryComponent,NoteDeleteComponent,ReminderEditComponent]
})
export class AppModule { }
