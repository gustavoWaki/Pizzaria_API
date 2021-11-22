namespace API_Pizzaria.Models
{
    public class PizzaSabores
    {
        public PizzaSabores(int id, string primeiroSabor, string segundoSabor)
        {
            this.id = id;
            this.primeiroSabor = primeiroSabor;
            this.segundoSabor = segundoSabor;
        }
        public int id {get;set;}
        public string primeiroSabor { get; set; }
        public string segundoSabor { get; set; }
    }
}