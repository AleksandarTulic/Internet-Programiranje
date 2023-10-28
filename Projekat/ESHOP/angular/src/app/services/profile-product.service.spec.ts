import { TestBed } from '@angular/core/testing';

import { ProfileProductService } from './profile-product.service';

describe('ProfileProductService', () => {
  let service: ProfileProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileProductService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
