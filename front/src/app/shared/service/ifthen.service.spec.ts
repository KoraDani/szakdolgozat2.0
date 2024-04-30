import { TestBed } from '@angular/core/testing';

import { IfthenService } from './ifthen.service';

describe('IfthenService', () => {
  let service: IfthenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IfthenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
