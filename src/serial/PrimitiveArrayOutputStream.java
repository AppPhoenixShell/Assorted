package serial;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class PrimitiveArrayOutputStream
{
	private ByteBuffer writeBuffer;

	private ByteArrayOutputStream arrayStream;
	
	public PrimitiveArrayOutputStream(int bufferSize) {
		//array = new byte[bufferSize];
		writeBuffer = ByteBuffer.allocate(bufferSize);
		arrayStream = new ByteArrayOutputStream(bufferSize);
	}
	public PrimitiveArrayOutputStream() {
		this(1000);
	}
	
	public byte[] toArray() {
		return arrayStream.toByteArray();
	}
	
	private void checkBuffer() {
		if(!hasLong())
			flushBuffer();
	}
	public void flushBuffer() {
		arrayStream.write(writeBuffer.array(),0, writeBuffer.position());
	}
	
	public boolean hasBytes(int bytes) {
		return writeBuffer.remaining() >= bytes;
	}
	public boolean hasLong() {
		return hasBytes(Long.BYTES);
	}
	
	
	public void write(float val) {
		checkBuffer();
	}
	public void write(double val) {
		checkBuffer();
	}
	
	public void write(int val) {
		
	}
	public void write(short val) {
		
	}
	
	public void write(long val) {
		
	}
	public void write(byte[] bytes) {
		if(!hasBytes(bytes.length))
			flushBuffer();
		writeBuffer.put(bytes);
	}
	public void write(byte val) {
		
	}
	public void writeUTF(String val) {
		byte[] bytes= val.getBytes(StandardCharsets.UTF_8);
		write(bytes);
		
	}
}
