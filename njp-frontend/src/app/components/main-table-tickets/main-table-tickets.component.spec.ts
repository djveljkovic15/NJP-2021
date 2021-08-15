import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainTableTicketsComponent } from './main-table-tickets.component';

describe('MainTableTicketsComponent', () => {
  let component: MainTableTicketsComponent;
  let fixture: ComponentFixture<MainTableTicketsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainTableTicketsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainTableTicketsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
