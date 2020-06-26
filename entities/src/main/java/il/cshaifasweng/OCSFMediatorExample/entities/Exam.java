package il.cshaifasweng.OCSFMediatorExample.entities;

import java.util.Calendar;
import java.util.List; 

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "exam")
public class Exam
{																		     
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	char[] code = new char[4];  //must be 4 digits check in teacher controller
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int number;
	
	double duration;
	
	
	
	String GeneralCommentTeacher;
	
	String GeneralCommentStudent;
	
    Boolean examExecutaion;
    
	@ManyToOne (cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;
	 
	@ManyToOne (cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	private Teacher createdByteacher;
	
	
	@ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE},targetEntity = Question.class)
	@JoinTable(name = "exam_question", joinColumns = @JoinColumn(name = "exam_id"),inverseJoinColumns = @JoinColumn(name = "question_id"))
	private List<Question> questions;
	
	@Column(name = "QuestionGrade")
	@ElementCollection //(targetClass=Double.class)
	private List<Double> QuestionGrade;
	
	@Column(name = "StudentComment")
	@ElementCollection// (targetClass=String.class)
	private List<String> studentComment;
	
	@Column(name = "TeacherComment")
    @ElementCollection //(targetClass = String.class)
	private List<String> teacherComment;
	
	


	

	//constructor
	
	public Exam(double duration, Course course, Teacher createdByteacher,String generalCommentTeacher,String generalCommentStudent, List<Double> grades,List<Question> questions,List<String> commentsStudent,List<String> commentsTeacher) {
		int examNum = course.getNum(); 
		examExecutaion = true;
		int subject_id = course.getSubject().getId();
		this.duration = duration;
		this.course = course;
		this.createdByteacher = createdByteacher;           
		course.addExam(this);								 
		this.questions = questions;
		this.GeneralCommentStudent = generalCommentStudent;
		this.GeneralCommentTeacher = generalCommentTeacher;
		this.id = this.course.getId() * 100 + subject_id * 10000 +  examNum; 
		this.course.setNum(); 
		for (Question question : questions)   
		{
			question.getExams().add(this); 
		}
		 
		this.course.addExam(this);  
		

	}
	
	

	public Exam() {
	}

	
	//methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char[] getCode() {
		return code;
	}

	public void setCode(char[] code) {
		this.code = code;
	}
	
	public void setTeacher(Teacher teacher) {
		this.createdByteacher = teacher;
	}
	
	public Teacher getTeacher()
	{
		return createdByteacher;
	}
	
	public Boolean getExamExecutaion() {
		return examExecutaion;
	}
	
	public void setExamExecutaion(Boolean executaion) {
		this.examExecutaion = executaion;
	}
	
	public String getGeneralCommentTeacher() {
		return GeneralCommentTeacher;
	}

	public void setGeneralCommentTeacher(String generalCommentTeacher) {
		GeneralCommentTeacher = generalCommentTeacher;
	}

	public String getGeneralCommentStudent() {
		return GeneralCommentStudent;
	}

	public void setGeneralCommentStudent(String generalCommentStudent) {
		GeneralCommentStudent = generalCommentStudent;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Teacher getCreatedByteacher() {
		return createdByteacher;
	}

	public void setCreatedByteacher(Teacher createdByteacher) {
		this.createdByteacher = createdByteacher;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public void setGrades(List<Double> gradesDoubles)
	{
		this.QuestionGrade = gradesDoubles;
	}
	
	public List<Double> getGrades()
	{
		return QuestionGrade;
	}



	public List<String> getStudentComment() {
		return studentComment;
	}



	public void setStudentComment(List<String> studentComment) {
		this.studentComment = studentComment;
	}



	public List<String> getTeacherComment() {
		return teacherComment;
	}



	public void setTeacherComment(List<String> teacherComment) {
		this.teacherComment = teacherComment;
	}
	
	
	

}
