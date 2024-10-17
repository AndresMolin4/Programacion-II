package onepiece;

    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.util.Scanner;
    import org.json.JSONArray;
    import org.json.JSONObject;

    
    
    public class OnePiece {
    
        // Método para consultar la API de One Piece y obtener información de un personaje
        public static String getOnePieceCharacterData(int index) {
            String urlString = "https://api.api-onepiece.com/v2/characters/en";
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            StringBuilder responseContent = new StringBuilder();
    
            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
    
                int status = connection.getResponseCode();
                if (status != 200) {
                    throw new RuntimeException("Error: " + status + " - No se pudo obtener la información del personaje.");
                }
    
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
    
        JSONArray onepieceJson = new JSONArray(responseContent.toString());
        JSONObject caracter = onepieceJson.getJSONObject(index);


        String nombre = caracter.optString("name", "Desconocido");
        String id = caracter.optString("id", "Desconocido");
        String recompensa = caracter.optString("bounty", "No disponible");
        String años = caracter.optString("age", "No disponible");
        String  altura = caracter.optString("size", "No disponible");  


        return "Nombre: " + nombre + "\nID: " + id + "\nBounty: " + recompensa + "\nCrew: " + años+"\nAltura: "+altura;

    
        
    
            } catch (Exception e) {
                e.printStackTrace();
                return "Error al obtener los datos del personaje.";
            } finally {
                try {
                    if (reader != null) 
                        reader.close();
                    if (connection != null) 
                        connection.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Ingrese el índice del personaje: ");
            int index = sc.nextInt();  // Cambiar la entrada a un número entero (índice)
            sc.close();
    
            System.out.println("Consultando datos del personaje en la posición " + index + "...");
            String caracterinfo  = getOnePieceCharacterData(index);
            System.out.println(caracterinfo);
        }
    }
    


