import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainAdminCreateticketComponent } from './main-admin-createticket.component';

describe('MainAdminCreateticketComponent', () => {
  let component: MainAdminCreateticketComponent;
  let fixture: ComponentFixture<MainAdminCreateticketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainAdminCreateticketComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainAdminCreateticketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
