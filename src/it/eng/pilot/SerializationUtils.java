package it.eng.pilot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Classe di utility per la clonazione ( deep copy) di oggetti tramite
 * serializzazione-deserializzazione
 * 
 * @author Antonio Corinaldi
 * 
 */
class SerializationUtils {
	public static <K extends Serializable> Object clone(K object) throws Exception {
		return deserialize(serialize(object));
	}

	public static Object deserialize(InputStream inputStream) throws Exception {
		if (inputStream == null) {
			throw new IllegalArgumentException("The InputStream must not be null");
		}
		ObjectInputStream in = null;
		Object localObject1 = null;
		try {
			in = new ObjectInputStream(inputStream);
			localObject1 = in.readObject();
		} catch (ClassNotFoundException ex) {
			throw new Exception(ex);
		} catch (IOException ex) {
			throw new Exception(ex);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException localIOException1) {
			}
		}
		return localObject1;
	}

	public static Object deserialize(byte[] objectData) throws Exception {
		if (objectData == null) {
			throw new IllegalArgumentException("The byte[] must not be null");
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
		return deserialize(bais);
	}

	public static byte[] serialize(Serializable obj) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
		serialize(obj, baos);
		return baos.toByteArray();
	}

	public static void serialize(Serializable obj, OutputStream outputStream) throws Exception {
		if (outputStream == null) {
			throw new IllegalArgumentException("The OutputStream must not be null");
		}
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(outputStream);
			out.writeObject(obj);
		} catch (IOException ex) {
			throw new Exception(ex);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException localIOException1) {
			}
		}
	}
}
