import { TestBed } from '@angular/core/testing';

import { GuardTokenLoginService } from './guard-token-login.service';

describe('GuardTokenLoginService', () => {
  let service: GuardTokenLoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuardTokenLoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
