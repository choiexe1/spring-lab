package devjay.springlab.domain.exception;

public class ItemNameExistException extends CustomException {
    @Override
    protected String defineErrorCode() {
        return "items.name.exist";
    }
}
