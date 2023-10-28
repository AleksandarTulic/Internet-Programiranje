import { TestBed } from '@angular/core/testing';

import { CategoryFieldsService } from './category-fields.service';

describe('CategoryFieldsService', () => {
  let service: CategoryFieldsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoryFieldsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
