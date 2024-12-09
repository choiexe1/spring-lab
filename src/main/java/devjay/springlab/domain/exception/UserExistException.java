package devjay.springlab.domain.exception;

public class UserExistException extends CustomException {
    
    @Override
    protected String defineErrorCode() {
        return "user.username.exist";
    }
}
