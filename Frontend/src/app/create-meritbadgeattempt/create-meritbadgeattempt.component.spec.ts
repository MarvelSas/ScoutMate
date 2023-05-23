import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMeritbadgeattemptComponent } from './create-meritbadgeattempt.component';

describe('CreateMeritbadgeattemptComponent', () => {
  let component: CreateMeritbadgeattemptComponent;
  let fixture: ComponentFixture<CreateMeritbadgeattemptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMeritbadgeattemptComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMeritbadgeattemptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
