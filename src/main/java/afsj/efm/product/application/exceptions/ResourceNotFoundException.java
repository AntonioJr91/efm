package afsj.efm.product.application.exceptions;

public final class ResourceNotFoundException extends RuntimeException {

   private ResourceNotFoundException(String message) {
      super(message);
   }

   public static ResourceNotFoundException byId(Long id) {
      return new ResourceNotFoundException("Resource with id '" + id + "' not found");
   }

   public static ResourceNotFoundException byName(String name) {
      return new ResourceNotFoundException("Resource with name '" + name + "' not found");
   }
}
