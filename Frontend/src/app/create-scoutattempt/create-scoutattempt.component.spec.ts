import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateScoutattemptComponent } from './create-scoutattempt.component';

describe('CreateScoutattemptComponent', () => {
  let component: CreateScoutattemptComponent;
  let fixture: ComponentFixture<CreateScoutattemptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateScoutattemptComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateScoutattemptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
