import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetrieveAllComponent } from './retrieve-all.component';

describe('RetrieveAllComponent', () => {
  let component: RetrieveAllComponent;
  let fixture: ComponentFixture<RetrieveAllComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RetrieveAllComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RetrieveAllComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
