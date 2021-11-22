using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using API_Pizzaria.Data;
using API_Pizzaria.Models;
namespace API_Pizzaria.Controllers
{
[Route("api/[controller]")]
[ApiController]
public class PedidoPizzaController : Controller
{
private readonly PizzariaContext _context;
public PedidoPizzaController(PizzariaContext context)
{
// construtor
_context = context;
}
[HttpGet]
public ActionResult<List<PedidoPizza>> GetAll() {
return _context.PedidoPizza.ToList();
}

[HttpGet("{PedidoId}")]
        public ActionResult<List<Pizza>> Get(int PedidoId)
        {
            try
            {
                var lista = _context.PedidoPizza.ToList();
                lista.Clear();
                var lista2 = _context.PedidoPizza.ToList();
                var lista3 = _context.Pizza.ToList();
                lista3.Clear();
                var lista4 = new List<PizzaSabores>();

                foreach(PedidoPizza pedidoPizza in lista2)
                {
                    if(pedidoPizza.idPedido == PedidoId){
                        lista.Add(pedidoPizza);
                    }
                }

                foreach(PedidoPizza pedidoPizza in lista)
                {
                    lista3.Add(_context.Pizza.Find(pedidoPizza.idPizza));
                }

                foreach(Pizza pizza in lista3)
                {
                    lista4.Add(new PizzaSabores(pizza.id, _context.Sabor.Find(pizza.primeiroSabor).nome, _context.Sabor.Find(pizza.segundoSabor).nome));
                }

                return Ok(lista4);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpDelete("{PizzaId}")]
        public async Task<ActionResult> delete(int PizzaId)
        {            
            try
            {
                var PedidoPizza = _context.PedidoPizza.SingleOrDefault(pedidopizza => pedidopizza.idPizza == PizzaId);
                var Pizza = _context.Pizza.Find(PizzaId);
                if (PedidoPizza == null||Pizza==null)
                {
                    //método do EF
                    return NotFound();
                }
                
                _context.Remove(PedidoPizza);
                await _context.SaveChangesAsync();

                _context.Remove(Pizza);
                await _context.SaveChangesAsync();
                return NoContent();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            // retorna BadRequest se não conseguiu deletar            
        }

        [HttpPost]
        public async Task<ActionResult> post(PedidoPizza model)
        {

            try
            {
                _context.PedidoPizza.Add(model);
                if (await _context.SaveChangesAsync() == 1)
                {
                    //return Ok();
                    return Created($"/api/pedidopizza/{model.id}", model);
                }
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }

            // retorna BadRequest se não conseguiu incluir
            return BadRequest();
        }
}
}