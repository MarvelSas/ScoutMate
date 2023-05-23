import { TestBed } from '@angular/core/testing';

import { OrgownerGuard } from './orgowner.guard';

describe('OrgownerGuard', () => {
  let guard: OrgownerGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(OrgownerGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
