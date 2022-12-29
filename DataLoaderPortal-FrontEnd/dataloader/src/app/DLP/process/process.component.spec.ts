import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { processComponent } from './process.component';

describe('UpdateComponent', () => {
  let component: processComponent;
  let fixture: ComponentFixture<processComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ processComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(processComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
