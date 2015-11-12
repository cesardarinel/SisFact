package entidades;

/**
 * Created by cesar on 11/12/2015.
 */
public class Listado {
        private String Id,Info,Texto1,Texto2;


        public Listado (String ID,  String Info,String Texto1,String texto2) {

            this.Id = ID;
            this.Info = Info;
            this.Texto1 =Texto1;
            this.Texto2 = texto2;
        }

        public String get_TextoID() {
            return Id;
        }


        public String get_Info() {
            return Info;
        }

        public String get_Texto1() {
            return Texto1;
        }

        public String get_Texto2() {
            return Texto2;
        }

    }