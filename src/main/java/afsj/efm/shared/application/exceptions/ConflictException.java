package afsj.efm.shared.application.exceptions;

public final class ConflictException extends RuntimeException {
   private ConflictException(String message) {
      super(message);
   }

   public static ConflictException of(String message) {
      return new ConflictException(message);
   }
}
