import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BadgesrankDashboardComponent } from './badgesrank-dashboard.component';

describe('BadgesrankDashboardComponent', () => {
  let component: BadgesrankDashboardComponent;
  let fixture: ComponentFixture<BadgesrankDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BadgesrankDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BadgesrankDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
