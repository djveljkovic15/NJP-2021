import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainAdminCreateuserComponent } from './main-admin-createuser.component';

describe('MainAdminCreateuserComponent', () => {
  let component: MainAdminCreateuserComponent;
  let fixture: ComponentFixture<MainAdminCreateuserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainAdminCreateuserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainAdminCreateuserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
