import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReminderEditComponent } from './reminder-edit.component';

describe('ReminderEditComponent', () => {
  let component: ReminderEditComponent;
  let fixture: ComponentFixture<ReminderEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReminderEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReminderEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
