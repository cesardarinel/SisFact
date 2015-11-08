package entidades;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;

@Table(name="facturas")
public class Facturas extends Model implements Parcelable{

	@Column(name="fecha")
	protected Date fecha;

	@Column(name="firma")
	protected String firma;

	@Column(name="contacto")
	protected Contactos contacto;

	protected Facturas(Parcel in) {
		fecha = new Date(in.readString());
		firma = in.readString();
		contacto = new Select().from(entidades.Contactos.class).where("id = ? ",in.readLong()).executeSingle();
	}



	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public Contactos getContacto() {
		return contacto;
	}

	public void setContacto(Contactos contacto) {
		this.contacto = contacto;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(fecha.toString());
		dest.writeString(firma);
		dest.writeLong(contacto.getId());
	}

	public static final Creator<Facturas> CREATOR = new Creator<Facturas>() {
		@Override
		public Facturas createFromParcel(Parcel in) {
			return new Facturas(in);
		}

		@Override
		public Facturas[] newArray(int size) {
			return new Facturas[size];
		}
	};
}
