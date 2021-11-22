using Microsoft.EntityFrameworkCore;
using API_Pizzaria.Models;

namespace API_Pizzaria.Data
{
    public class PizzariaContext : DbContext
    {
        public PizzariaContext(DbContextOptions<PizzariaContext> options): base (options)
{
}
public DbSet<Cliente> Cliente {get; set;}
public DbSet<Sabor> Sabor {get; set;}
public DbSet<Ingrediente> Ingrediente {get; set;}
public DbSet<ClienteSabor> ClienteSabor {get; set;}
public DbSet<SaborIngrediente> SaborIngrediente {get; set;}
public DbSet<Pedido> Pedido {get; set;}
public DbSet<Pizza> Pizza {get; set;}
public DbSet<PedidoPizza> PedidoPizza {get; set;}
public DbSet<Usuario> Usuario {get; set;}
    }
}