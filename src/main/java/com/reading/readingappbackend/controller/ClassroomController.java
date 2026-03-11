package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.Classroom;
import com.reading.readingappbackend.model.CreateClassroomRequest;
import com.reading.readingappbackend.model.JoinClassroomRequest;
import com.reading.readingappbackend.model.User;
import com.reading.readingappbackend.repository.ClassroomRepository;
import com.reading.readingappbackend.repository.UserRepository;
import com.reading.readingappbackend.model.AddTeacherToClassRequest;
import com.reading.readingappbackend.model.ClassroomTeacher;
import com.reading.readingappbackend.model.RemoveStudentFromClassRequest;
import com.reading.readingappbackend.repository.ClassroomTeacherRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final ClassroomTeacherRepository classroomTeacherRepository;

    public ClassroomController(ClassroomRepository classroomRepository,
                               UserRepository userRepository,
                               ClassroomTeacherRepository classroomTeacherRepository) {
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.classroomTeacherRepository = classroomTeacherRepository;
    }

    // 1. 老师创建班级
    @PostMapping
    public Object createClassroom(@RequestBody CreateClassroomRequest request) {

        if (request.getName() == null || request.getName().isBlank()) {
            return Map.of("error", "班级名称不能为空");
        }

        if (request.getTeacherUsername() == null || request.getTeacherUsername().isBlank()) {
            return Map.of("error", "老师用户名不能为空");
        }

        Optional<User> teacherOptional = userRepository.findByUsername(request.getTeacherUsername());

        if (teacherOptional.isEmpty()) {
            return Map.of("error", "老师账号不存在");
        }

        User teacher = teacherOptional.get();

        if (!"teacher".equals(teacher.getRole())) {
            return Map.of("error", "该账号不是老师");
        }

        Classroom classroom = new Classroom(
                request.getName(),
                request.getTeacherUsername()
        );

        Classroom savedClassroom = classroomRepository.save(classroom);

// 创建班级的人，自动成为班主任
        ClassroomTeacher homeroomTeacher = new ClassroomTeacher(
                savedClassroom.getId(),
                request.getTeacherUsername(),
                "HOMEROOM"
        );

        classroomTeacherRepository.save(homeroomTeacher);

        return savedClassroom;
    }

    // 2. 学生加入班级
    @PostMapping("/{classroomId}/join")
    public Object joinClassroom(@PathVariable Long classroomId,
                                @RequestBody JoinClassroomRequest request) {

        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);

        if (classroomOptional.isEmpty()) {
            return Map.of("error", "班级不存在");
        }

        if (request.getStudentUsername() == null || request.getStudentUsername().isBlank()) {
            return Map.of("error", "学生用户名不能为空");
        }

        Optional<User> studentOptional = userRepository.findByUsername(request.getStudentUsername());

        if (studentOptional.isEmpty()) {
            return Map.of("error", "学生账号不存在");
        }

        User student = studentOptional.get();

        if (!"student".equals(student.getRole())) {
            return Map.of("error", "该账号不是学生");
        }

        student.setClassroomId(classroomId);
        userRepository.save(student);

        return Map.of(
                "message", "加入班级成功",
                "classroomId", classroomId,
                "studentUsername", student.getUsername()
        );
    }

    // 3. 老师查看自己创建的所有班级
    @GetMapping("/teacher/{teacherUsername}")
    public List<Classroom> getTeacherClassrooms(@PathVariable String teacherUsername) {
        return classroomRepository.findByTeacherUsername(teacherUsername);
    }

    // 4. 查看某个班级里的学生
    @GetMapping("/{classroomId}/students")
    public List<User> getStudentsInClassroom(@PathVariable Long classroomId) {
        return userRepository.findByClassroomId(classroomId);
    }

    // 5. 班主任添加任课老师进班
    @PostMapping("/{classroomId}/teachers")
    public Object addTeacherToClassroom(@PathVariable Long classroomId,
                                        @RequestBody AddTeacherToClassRequest request) {

        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);

        if (classroomOptional.isEmpty()) {
            return Map.of("error", "班级不存在");
        }

        Classroom classroom = classroomOptional.get();

        if (request.getHomeroomTeacherUsername() == null || request.getHomeroomTeacherUsername().isBlank()) {
            return Map.of("error", "班主任用户名不能为空");
        }

        if (request.getTeacherUsername() == null || request.getTeacherUsername().isBlank()) {
            return Map.of("error", "要添加的老师用户名不能为空");
        }

        // 校验当前操作人是不是这个班的班主任
        if (!classroom.getTeacherUsername().equals(request.getHomeroomTeacherUsername())) {
            return Map.of("error", "只有班主任可以添加老师");
        }

        Optional<User> teacherOptional = userRepository.findByUsername(request.getTeacherUsername());

        if (teacherOptional.isEmpty()) {
            return Map.of("error", "老师账号不存在");
        }

        User teacher = teacherOptional.get();

        if (!"teacher".equals(teacher.getRole())) {
            return Map.of("error", "该账号不是老师");
        }

        Optional<ClassroomTeacher> existing = classroomTeacherRepository
                .findByClassroomIdAndTeacherUsername(classroomId, request.getTeacherUsername());

        if (existing.isPresent()) {
            return Map.of("error", "该老师已经在这个班级中");
        }

        ClassroomTeacher classroomTeacher = new ClassroomTeacher(
                classroomId,
                request.getTeacherUsername(),
                "SUBJECT"
        );

        classroomTeacherRepository.save(classroomTeacher);

        return Map.of(
                "message", "添加老师成功",
                "classroomId", classroomId,
                "teacherUsername", request.getTeacherUsername()
        );
    }
    // 6. 查看某个班级里的老师
    @GetMapping("/{classroomId}/teachers")
    public List<ClassroomTeacher> getTeachersInClassroom(@PathVariable Long classroomId) {
        return classroomTeacherRepository.findByClassroomId(classroomId);
    }
    // 7. 班主任把学生移出班级
    @PostMapping("/{classroomId}/remove-student")
    public Object removeStudentFromClassroom(@PathVariable Long classroomId,
                                             @RequestBody RemoveStudentFromClassRequest request) {

        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);

        if (classroomOptional.isEmpty()) {
            return Map.of("error", "班级不存在");
        }

        Classroom classroom = classroomOptional.get();

        if (request.getHomeroomTeacherUsername() == null || request.getHomeroomTeacherUsername().isBlank()) {
            return Map.of("error", "班主任用户名不能为空");
        }

        if (request.getStudentUsername() == null || request.getStudentUsername().isBlank()) {
            return Map.of("error", "学生用户名不能为空");
        }

        // 校验是不是班主任
        if (!classroom.getTeacherUsername().equals(request.getHomeroomTeacherUsername())) {
            return Map.of("error", "只有班主任可以移出学生");
        }

        Optional<User> studentOptional = userRepository.findByUsername(request.getStudentUsername());

        if (studentOptional.isEmpty()) {
            return Map.of("error", "学生账号不存在");
        }

        User student = studentOptional.get();

        if (!"student".equals(student.getRole())) {
            return Map.of("error", "该账号不是学生");
        }

        if (student.getClassroomId() == null || !student.getClassroomId().equals(classroomId)) {
            return Map.of("error", "该学生不在这个班级中");
        }

        student.setClassroomId(null);
        userRepository.save(student);

        return Map.of(
                "message", "移出学生成功",
                "studentUsername", student.getUsername()
        );
    }
}