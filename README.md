User Management System

Project quản lý thông tin User
- Dự án được áp dụng theo hướng Clean Architecture (tách biệt các đối tượng thực thể và domain nghiệp vụ cần xử lý với các dependencies)
- Domain: Module chứa thông tin các đối tượng chính (entities: User, Activity).
- Application: Với project hiện tại chưa được kết hợp với Domain Driven Design nên các nghiệp vụ xử lý theo từng use case 
  sẽ được tách biệt ở module application(CheckHistory, CalculatePoints, LeaderBoard,...).
- Infrastructure: Module lưu trữ các Adapters và Controllers.
  + Input adapters (Repository) từ Java Collections API và sau này có thể mở rộng hoặc chuyển đổi qua sử dụng JDBC, JPA, hoặc từ third party.
  + Output adapters (Controller) hiện tại đang sử dụng console của Java SE và có thể mở rộng chuyển đổi qua API, qua Web View,...