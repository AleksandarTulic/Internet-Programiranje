import { TestBed } from '@angular/core/testing';

import { GuardSignUpService } from './guard-sign-up.service';

describe('GuardSignUpService', () => {
  let service: GuardSignUpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuardSignUpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
