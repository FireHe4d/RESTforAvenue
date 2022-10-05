package kz.com.aven.Response;

import kz.com.aven.Entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {
    private Long id;
    private Status oldStatus;
    private Status newStatus;
}