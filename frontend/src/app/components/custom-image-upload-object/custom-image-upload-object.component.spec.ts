import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomImageUploadObjectComponent } from './custom-image-upload-object.component';

describe('CustomImageUploadObjectComponent', () => {
  let component: CustomImageUploadObjectComponent;
  let fixture: ComponentFixture<CustomImageUploadObjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomImageUploadObjectComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomImageUploadObjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
