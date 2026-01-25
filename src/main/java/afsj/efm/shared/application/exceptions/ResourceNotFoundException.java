package afsj.efm.shared.application.exceptions;

public final class ResourceNotFoundException extends RuntimeException {
   private ResourceNotFoundException(String message) {
      super(message);
   }

   public static ResourceNotFoundException of(String message) {
      return new ResourceNotFoundException(message);
   }
}
