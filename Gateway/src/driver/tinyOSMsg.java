package driver;

/**
 * This class is automatically generated by mig. DO NOT EDIT THIS FILE.
 * This class implements a Java interface to the 'baseMsg'
 * message type.
 */

public class tinyOSMsg extends net.tinyos.message.Message {

    /** The default size of this message type in bytes. */
    public static final int DEFAULT_MESSAGE_SIZE = 14;

    /** The Active Message type associated with this message. */
    public static final int AM_TYPE = 7;

    /** Create a new baseMsg of size 14. */
    public tinyOSMsg() {
        super(DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /** Create a new baseMsg of the given data_length. */
    public tinyOSMsg(int data_length) {
        super(data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new baseMsg with the given data_length
     * and base offset.
     */
    public tinyOSMsg(int data_length, int base_offset) {
        super(data_length, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new baseMsg using the given byte array
     * as backing store.
     */
    public tinyOSMsg(byte[] data) {
        super(data);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new baseMsg using the given byte array
     * as backing store, with the given base offset.
     */
    public tinyOSMsg(byte[] data, int base_offset) {
        super(data, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new baseMsg using the given byte array
     * as backing store, with the given base offset and data length.
     */
    public tinyOSMsg(byte[] data, int base_offset, int data_length) {
        super(data, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new baseMsg embedded in the given message
     * at the given base offset.
     */
    public tinyOSMsg(net.tinyos.message.Message msg, int base_offset) {
        super(msg, base_offset, DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new baseMsg embedded in the given message
     * at the given base offset and length.
     */
    public tinyOSMsg(net.tinyos.message.Message msg, int base_offset, int data_length) {
        super(msg, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
    /* Return a String representation of this message. Includes the
     * message type name and the non-indexed field values.
     */
    public String toString() {
      String s = "Message <baseMsg> \n";
      try {
        s += "  [metodo=0x"+Long.toHexString(get_metodo())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [path=0x"+Long.toHexString(get_path())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [dados=0x"+Long.toHexString(get_dados())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [dhost=0x"+Long.toHexString(get_dhost())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [shost=0x"+Long.toHexString(get_shost())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [codigo=0x"+Long.toHexString(get_codigo())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [error=0x"+Long.toHexString(get_error())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      return s;
    }

    // Message-type-specific access methods appear below.

    /////////////////////////////////////////////////////////
    // Accessor methods for field: metodo
    //   Field type: int, unsigned
    //   Offset (bits): 0
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'metodo' is signed (false).
     */
    public static boolean isSigned_metodo() {
        return false;
    }

    /**
     * Return whether the field 'metodo' is an array (false).
     */
    public static boolean isArray_metodo() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'metodo'
     */
    public static int offset_metodo() {
        return (0 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'metodo'
     */
    public static int offsetBits_metodo() {
        return 0;
    }

    /**
     * Return the value (as a int) of the field 'metodo'
     */
    public int get_metodo() {
        return (int)getUIntBEElement(offsetBits_metodo(), 16);
    }

    /**
     * Set the value of the field 'metodo'
     */
    public void set_metodo(int value) {
        setUIntBEElement(offsetBits_metodo(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'metodo'
     */
    public static int size_metodo() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'metodo'
     */
    public static int sizeBits_metodo() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: path
    //   Field type: int, unsigned
    //   Offset (bits): 16
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'path' is signed (false).
     */
    public static boolean isSigned_path() {
        return false;
    }

    /**
     * Return whether the field 'path' is an array (false).
     */
    public static boolean isArray_path() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'path'
     */
    public static int offset_path() {
        return (16 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'path'
     */
    public static int offsetBits_path() {
        return 16;
    }

    /**
     * Return the value (as a int) of the field 'path'
     */
    public int get_path() {
        return (int)getUIntBEElement(offsetBits_path(), 16);
    }

    /**
     * Set the value of the field 'path'
     */
    public void set_path(int value) {
        setUIntBEElement(offsetBits_path(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'path'
     */
    public static int size_path() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'path'
     */
    public static int sizeBits_path() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: dados
    //   Field type: int, unsigned
    //   Offset (bits): 32
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'dados' is signed (false).
     */
    public static boolean isSigned_dados() {
        return false;
    }

    /**
     * Return whether the field 'dados' is an array (false).
     */
    public static boolean isArray_dados() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'dados'
     */
    public static int offset_dados() {
        return (32 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'dados'
     */
    public static int offsetBits_dados() {
        return 32;
    }

    /**
     * Return the value (as a int) of the field 'dados'
     */
    public int get_dados() {
        return (int)getUIntBEElement(offsetBits_dados(), 16);
    }

    /**
     * Set the value of the field 'dados'
     */
    public void set_dados(int value) {
        setUIntBEElement(offsetBits_dados(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'dados'
     */
    public static int size_dados() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'dados'
     */
    public static int sizeBits_dados() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: dhost
    //   Field type: int, unsigned
    //   Offset (bits): 48
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'dhost' is signed (false).
     */
    public static boolean isSigned_dhost() {
        return false;
    }

    /**
     * Return whether the field 'dhost' is an array (false).
     */
    public static boolean isArray_dhost() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'dhost'
     */
    public static int offset_dhost() {
        return (48 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'dhost'
     */
    public static int offsetBits_dhost() {
        return 48;
    }

    /**
     * Return the value (as a int) of the field 'dhost'
     */
    public int get_dhost() {
        return (int)getUIntBEElement(offsetBits_dhost(), 16);
    }

    /**
     * Set the value of the field 'dhost'
     */
    public void set_dhost(int value) {
        setUIntBEElement(offsetBits_dhost(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'dhost'
     */
    public static int size_dhost() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'dhost'
     */
    public static int sizeBits_dhost() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: shost
    //   Field type: int, unsigned
    //   Offset (bits): 64
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'shost' is signed (false).
     */
    public static boolean isSigned_shost() {
        return false;
    }

    /**
     * Return whether the field 'shost' is an array (false).
     */
    public static boolean isArray_shost() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'shost'
     */
    public static int offset_shost() {
        return (64 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'shost'
     */
    public static int offsetBits_shost() {
        return 64;
    }

    /**
     * Return the value (as a int) of the field 'shost'
     */
    public int get_shost() {
        return (int)getUIntBEElement(offsetBits_shost(), 16);
    }

    /**
     * Set the value of the field 'shost'
     */
    public void set_shost(int value) {
        setUIntBEElement(offsetBits_shost(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'shost'
     */
    public static int size_shost() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'shost'
     */
    public static int sizeBits_shost() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: codigo
    //   Field type: int, unsigned
    //   Offset (bits): 80
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'codigo' is signed (false).
     */
    public static boolean isSigned_codigo() {
        return false;
    }

    /**
     * Return whether the field 'codigo' is an array (false).
     */
    public static boolean isArray_codigo() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'codigo'
     */
    public static int offset_codigo() {
        return (80 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'codigo'
     */
    public static int offsetBits_codigo() {
        return 80;
    }

    /**
     * Return the value (as a int) of the field 'codigo'
     */
    public int get_codigo() {
        return (int)getUIntBEElement(offsetBits_codigo(), 16);
    }

    /**
     * Set the value of the field 'codigo'
     */
    public void set_codigo(int value) {
        setUIntBEElement(offsetBits_codigo(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'codigo'
     */
    public static int size_codigo() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'codigo'
     */
    public static int sizeBits_codigo() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: error
    //   Field type: int, unsigned
    //   Offset (bits): 96
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'error' is signed (false).
     */
    public static boolean isSigned_error() {
        return false;
    }

    /**
     * Return whether the field 'error' is an array (false).
     */
    public static boolean isArray_error() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'error'
     */
    public static int offset_error() {
        return (96 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'error'
     */
    public static int offsetBits_error() {
        return 96;
    }

    /**
     * Return the value (as a int) of the field 'error'
     */
    public int get_error() {
        return (int)getUIntBEElement(offsetBits_error(), 16);
    }

    /**
     * Set the value of the field 'error'
     */
    public void set_error(int value) {
        setUIntBEElement(offsetBits_error(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'error'
     */
    public static int size_error() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'error'
     */
    public static int sizeBits_error() {
        return 16;
    }

}
