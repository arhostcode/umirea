package edu.mirea.ardyc.umirea.data.dataSources.room.group.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.LessonEntity;
import edu.mirea.ardyc.umirea.data.model.group.Member;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;

@Entity(tableName = "group_members")
public class MemberEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "unique_id")
    private String uniqueId;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    @ColumnInfo(name = "role")
    private String role;


    public MemberEntity(String uniqueId, String firstName, String lastName, String imagePath, String role) {
        this.uniqueId = uniqueId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imagePath = imagePath;
        this.role = role;
    }

    public MemberEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static MemberEntity fromMember(Member member) {
        return new MemberEntity(member.getUuid(), member.getFirstName(), member.getLastName(), member.getImagePath(), member.getRole());
    }

    public Member toMember() {
        return new Member(firstName, lastName, role, uniqueId, imagePath);
    }

}
