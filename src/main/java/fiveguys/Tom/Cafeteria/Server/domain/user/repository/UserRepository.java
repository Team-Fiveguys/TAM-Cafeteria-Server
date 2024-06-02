package fiveguys.Tom.Cafeteria.Server.domain.user.repository;


import fiveguys.Tom.Cafeteria.Server.domain.user.entity.Role;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findBySocialId(String socialId);
    public Optional<User> findByEmail(String email);

    public boolean existsBySocialId(String socialId);

    public boolean existsByEmail(String email);

    public Page<User> findAll(Pageable pageable);

    public List<User> findAllByRole(Role role);

    List<User> findByNotificationSetIsNotNull();

    /*
    오늘의 식단 알림을 받을 사용자를 조회 하는 쿼리 (모두, 명진당만, 학관만)
     */
    @Query("SELECT u FROM users u WHERE u.notificationSet.todayDiet = true AND u.notificationSet.myeongJin = true AND u.notificationSet.hakGwan = true")
    List<User> findByAcceptedTodayDietAndAll();

    @Query("SELECT u FROM users u WHERE u.notificationSet.todayDiet = true AND u.notificationSet.myeongJin = true AND u.notificationSet.hakGwan = false")
    List<User> findByAcceptedTodayDietAndOnlyMyeongJin();

    @Query("SELECT u FROM users u WHERE u.notificationSet.todayDiet = true AND u.notificationSet.myeongJin = false AND u.notificationSet.hakGwan = true")
    List<User> findByAcceptedTodayDietAndOnlyHakGwan();

    @Query("SELECT u FROM users u WHERE u.notificationSet.todayDiet = true AND u.notificationSet.myeongJin = true")
    public List<User> findAllByAcceptedTodayDietAndMyeongJin();
    @Query("SELECT u FROM users u WHERE u.notificationSet.todayDiet = true AND u.notificationSet.hakGwan = true")
    public List<User> findAllByAcceptedTodayDietAndHakGwan();
    @Query("SELECT u FROM users u WHERE u.notificationSet.todayDiet = true AND u.notificationSet.myeongDon = true")
    public List<User> findAllByAcceptedTodayDietAndMyeongDon();
    @Query("SELECT u FROM users u WHERE u.notificationSet.weekDietEnroll = true AND u.notificationSet.myeongJin = true")
    public List<User> findAllByAcceptedWeekDietEnrollAndMyeongJin();

    @Query("SELECT u FROM users u WHERE u.notificationSet.weekDietEnroll = true AND u.notificationSet.hakGwan = true")
    public List<User> findAllByAcceptedWeekDietEnrollAndHakGwan();
    @Query("SELECT u FROM users u WHERE u.notificationSet.weekDietEnroll = true AND u.notificationSet.myeongDon = true")
    public List<User> findAllByAcceptedWeekDietEnrollAndMyeongDon();


    @Query("SELECT u FROM users u WHERE u.notificationSet.dietPhotoEnroll = true AND u.notificationSet.myeongJin = true")
    public List<User> findAllByAcceptedDietPhotoEnrollAndMyeongJin();

    @Query("SELECT u FROM users u WHERE u.notificationSet.dietPhotoEnroll = true AND u.notificationSet.hakGwan = true")
    public List<User> findAllByAcceptedDietPhotoEnrollAndHakGwan();
    @Query("SELECT u FROM users u WHERE u.notificationSet.dietPhotoEnroll = true AND u.notificationSet.myeongDon = true")
    public List<User> findAllByAcceptedDietPhotoEnrollAndMyeongDon();

    @Query("SELECT u FROM users u WHERE u.notificationSet.dietSoldOut = true AND u.notificationSet.myeongJin = true")
    public List<User> findAllByAcceptedDietSoldOutAndMyeongJin();
    @Query("SELECT u FROM users u WHERE u.notificationSet.dietSoldOut = true AND u.notificationSet.hakGwan = true")
    public List<User> findAllByAcceptedDietSoldOutAndHakGwan();
    @Query("SELECT u FROM users u WHERE u.notificationSet.dietSoldOut = true AND u.notificationSet.myeongDon = true")
    public List<User> findAllByAcceptedDietSoldOutAndMyeongDon();

    @Query("SELECT u FROM users u WHERE u.notificationSet.general = true AND u.notificationSet.myeongJin = true")
    public List<User> findAllByAcceptedGeneralAndMyeongJin();
    @Query("SELECT u FROM users u WHERE u.notificationSet.general = true AND u.notificationSet.hakGwan = true")
    public List<User> findAllByAcceptedGeneralAndHakGwan();
    @Query("SELECT u FROM users u WHERE u.notificationSet.general = true AND u.notificationSet.myeongDon = true")
    public List<User> findAllByAcceptedGeneralAndMyeongDon();

}
