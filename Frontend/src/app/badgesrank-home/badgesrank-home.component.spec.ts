import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BadgesrankHomeComponent } from './badgesrank-home.component';

describe('BadgesrankHomeComponent', () => {
  let component: BadgesrankHomeComponent;
  let fixture: ComponentFixture<BadgesrankHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BadgesrankHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BadgesrankHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
