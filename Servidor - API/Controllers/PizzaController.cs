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
public class PizzaController : Controller
{
private readonly PizzariaContext _context;
public PizzaController(PizzariaContext context)
{
// construtor
_context = context;
}
[HttpGet]
public ActionResult<List<Pizza>> GetAll() {
return _context.Pizza.ToList();
}

[HttpGet("{PizzaId}")]
        public ActionResult<List<Pizza>> Get(int PizzaId)
        {
            try
            {
                var result = _context.Pizza.Find(PizzaId);
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

        [HttpDelete("{PizzaId}")]
        public async Task<ActionResult> delete(int PizzaId)
        {            
            try
            {
                //verifica se existe Pizza a ser excluído
                var Pizza = await _context.Pizza.FindAsync(PizzaId);
                if (Pizza == null)
                {
                    //método do EF
                    return NotFound();
                }
                
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
        public async Task<ActionResult> post(Pizza model)
        {

            try
            {
                _context.Pizza.Add(model);
                if (await _context.SaveChangesAsync() == 1)
                {
                    //return Ok();
                    return Created($"/api/pizza/{model.id}", model);
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