
- This is Admin Netflix Application
# Netflix application for admin

![firstscreen](https://user-images.githubusercontent.com/45253067/162468129-7567fc2e-e392-49ba-a2b6-acc5b7034036.gif)

Admin:
- Đăng ký tài khoản admin: post --> http://localhost:5000/api/auth/admin/register ✔
- Xác thực email đăng ký: get --> http://localhost:5000/api/auth/admin/verify/:userId/:uniqueString ✔
- Đăng nhập tài khoản admin: post --> http://localhost:5000/api/auth/admin/login ✔
- Đăng xuất tài khoản: get --> http://localhost:5000/api/auth/admin/logout ✔
- Xem profile: get --> http://localhost:5000/api/auth/admin/profile ✔
- Chỉnh sửa profile: patch --> http://localhost:5000/api/auth/admin/profile/update ✔
- Refresh token : get --> http://localhost:5000/api/auth/admin/refresh_token ✔
- Thay đổi mật khẩu : patch --> http://localhost:5000/api/auth/admin/changePassword ✔
- Quên mật khẩu tài khoản admin: post --> http://localhost:5000/api/auth/admin/forget ✔
- Link reset mật khẩu khi quên: put --> http://localhost:5000/api/auth/admin/password/reset/:token ✔
- Đăng nhập google tài khoản admin: post --> http://localhost:5000/api/auth/admin/loginGoogle ✔
- Danh sách tài khoản admin: get --> http://localhost:5000/api/auth/admin/getAllAdmin ✔
- Danh sách tài khoản khách hàng: get --> http://localhost:5000/api/auth/admin/getAllCustomer ✔
- Xem chi tiết tài khoản khách hàng: get --> http://localhost:5000/api/auth/admin/getDetailCustomer/:id ✔
- Chỉnh sửa thông tin tài khoản khách hàng: patch --> http://localhost:5000/api/auth/admin/customerAccount/:id/update/info
- Danh sách khách hàng chưa check email đăng ký: get --> http://localhost:5000/api/auth/admin/getAllCustomerUncheck

Upload:
- Upload ảnh người dùng : post --> http://localhost:5000/api/uploadImageUser
- Xóa ảnh người dùng trên cloud : post --> http://localhost:5000/api/destroyImageUser
- Upload video phim : post --> http://localhost:5000/api/uploadVideoFilm ✔
- Xóa video phim trên cloud : post --> http://localhost:5000/api/destroyVideoFilm
- Upload ảnh đạo diễn : post --> http://localhost:5000/api/uploadImageDirector ✔
- Xóa ảnh đạo diễn trên cloud : post --> http://localhost:5000/api/destroyImageDirector ✔
- Upload ảnh phim : post --> http://localhost:5000/api/uploadImageFil ✔
- Xóa ảnh phim : post --> http://localhost:5000/api/destroyImageFilm
- Upload ảnh hình thức thanh toán : post --> http://localhost:5000/api/uploadImagePayment ✔
- Xóa ảnh hình thức thanh toán : post --> http://localhost:5000/api/destroyImagePayment ✔

Director:
- Xem thông tin tất cả đạo diễn: get --> http://localhost:5000/api/director/all ✔
- Xem thông tin chi tiết đạo diễn: get --> http://localhost:5000/api/director/:id ✔
- Thêm mới thông tin đạo diễn : post --> http://localhost:5000/api/director/add ✔
- Chỉnh sửa thông tin đạo diễn : patch --> http://localhost:5000/api/director/update/:id ✔
- Xóa thông tin đạo diễn : delete --> http://localhost:5000/api/director/delete/:id ✔

Category
- Xem tất cả thể loại phim : get --> http://localhost:5000/api/category/all ✔
- Tạo thêm 1 thể loại phim: post --> http://localhost:5000/api/category/add ✔
- Cập nhập thể loại phim: put --> http://localhost:5000/api/category/update/:id ✔
- Xóa thể loại phim: delete --> http://localhost:5000/api/category/delete/:id ✔

Film
- Xem thông tin tất cả bộ phim: get --> http://localhost:5000/api/film/all ✔
- Thêm thông tin bộ phim: post --> http://localhost:5000/api/film/add ✔
- Chỉnh sửa thông tin bộ phim: patch --> http://localhost:5000/api/film/update/:id
- Xóa bộ phim : delete --> http://localhost:5000/api/film/delete/:id ✔
- Thông tin chi tiết của bộ phim và thông tin đánh giá của bộ phim: get --> http://localhost:5000/api/film/detail/:id
- Tìm bộ phim theo thể loại: get --> http://localhost:5000/api/film/find/category/:id
- Tìm bộ phim theo đạo diễn: get --> http://localhost:5000/api/film/find/director/:id
- Thêm một tập phim: post --> http://localhost:5000/api/film/:id/addEpisode
- Cập nhập tập phim: patch --> http://localhost:5000/api/film/update/:filmId/updateEpisode/:episodeId
- Xóa 1 tập phim: delete --> http://localhost:5000/api/film/delete/:filmId/deleteEpisode/:episodeId

Favourite 
- Xem danh sách yêu thích của toàn bộ người dùng: get --> http://localhost:5000/api/favourite/all ✔
 
Rating
- Xem đánh giá của toàn bộ người dùng: get --> http://localhost:5000/api/rating/all ✔

Comment
- Xem tất cả bình luận: get --> http://localhost:5000/api/comment/all
- Xem bình luận của bộ phim: get --> http://localhost:5000/api/comment/get/:filmId
- Lấy thùng rác chứa những bình luận đã bị xóa: get --> http//localhost:5000/api/comment/bin
- Khôi phục lại comment đã bị xóa: patch --> http://localhost:5000/api/comment/:id/restore
- Xóa hẳn comment nếu ở trong thùng rác quá 7 ngày: delete --> http://localhost:5000/api/comment/bin/delete

Payment
- Xem tất cả hình thức thanh toán: get --> http://localhost:5000/api/modeOfPayment/all ✔
- Xem chi tiết hình thức thanh toán: get --> http://localhost:5000/api/modeOfPayment/:id ✔ 
- Thêm hình thức thanh toán mới: post --> http://localhost:5000/api/modeOfPayment/add ✔
- Chỉnh sửa hình thức thanh toán: patch --> http://localhost:5000/api/modeOfPayment/update/:id ✔
- Xóa hình thức thanh toán: delete --> http://localhost:5000/api/modeOfPayment/delete/:id ✔

Feedback
- Xem tất cả feedback: get --> http://localhost:5000/api/feedback/all ✔
- Trả lời feedback của khách hàng: post --> http://localhost:5000/api/feedback/response/:id ✔

Bill
- Lấy ra toàn bộ hóa đơn: get -> http://localhost:5000/api/bill/all ✔

