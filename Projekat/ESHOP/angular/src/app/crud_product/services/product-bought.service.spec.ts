import { TestBed } from '@angular/core/testing';

import { ProductBoughtService } from './product-bought.service';

describe('ProductBoughtService', () => {
  let service: ProductBoughtService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductBoughtService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
