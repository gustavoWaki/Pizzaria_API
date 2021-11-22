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
public class PedidoController : Controller
{
private readonly PizzariaContext _context;
public PedidoController(PizzariaContext context)
{
// construtor
_context = context;
}
[HttpGet]
public ActionResult<List<Pedido>> GetAll() {
            try
            {
                var result = _context.Pedido.ToList()[0];
                return Ok(result);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
}

[HttpGet("{PedidoId}")]
        public ActionResult<List<Pedido>> Get(int PedidoId)
        {
            try
            {
                var result = _context.Pedido.Find(PedidoId);
                if (result == null)
                {
                    return NotFound();
                }
                return Ok(result);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpDelete("{PedidoId}")]
        public async Task<ActionResult> delete(int PedidoId)
        {            
            try
            {
                //verifica se existe Pedido a ser excluído
                var Pedido = await _context.Pedido.FindAsync(PedidoId);
                if (Pedido == null)
                {
                    //método do EF
                    return NotFound();
                }
                
                _context.Remove(Pedido);
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
        public async Task<ActionResult> post(Pedido model)
        {

            try
            {
                _context.Pedido.Add(model);
                if (await _context.SaveChangesAsync() == 1)
                {
                    //return Ok();
                    return Created($"/api/pedido/{model.id}", model);
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