package CampusTest.Classes;

import java.util.Arrays;

public class US_3_DocumentTypes {

 private String id=null;
 private String name;

 private String description;

 private String schoolId;

  private String[] attachmentStages;

  private boolean active=true;
  private boolean required=true;
  private boolean useCamera=false;
  private String[] translateName={};

 public US_3_DocumentTypes(String schoolId, String[] attachmentStages) {
  this.schoolId = schoolId;
  this.attachmentStages = attachmentStages;
 }

 public boolean isActive() {
  return active;
 }

 public void setActive(boolean active) {
  this.active = active;
 }

 public boolean isRequired() {
  return required;
 }

 public void setRequired(boolean required) {
  this.required = required;
 }

 public boolean isUseCamera() {
  return useCamera;
 }

 public void setUseCamera(boolean useCamera) {
  this.useCamera = useCamera;
 }

 public String[] getTranslateName() {
  return translateName;
 }

 public void setTranslateName(String[] translateName) {
  this.translateName = translateName;
 }

 public String[] getAttachmentStages() {
  return attachmentStages;
 }

 public void setAttachmentStages(String[] attachmentStages) {
  this.attachmentStages = attachmentStages;
 }

 public String getId() {
  return id;
 }

 public void setId(String id) {
  this.id = id;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }


 public String getDescription() {
  return description;
 }

 public void setDescription(String description) {
  this.description = description;
 }

 public String getSchoolId() {
  return schoolId;
 }

 public void setSchoolId(String schoolId) {
  this.schoolId = schoolId;
 }

}
