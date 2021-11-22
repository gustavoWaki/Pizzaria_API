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
public class IngredienteController : Controller
{
private readonly PizzariaContext _context;
public IngredienteController(PizzariaContext context)
{
// construtor
_context = context;
}
[HttpGet]
public ActionResult<List<Ingrediente>> GetAll() {
return _context.Ingrediente.ToList();
}

[HttpGet("{IngredienteId}")]
        public ActionResult<List<Ingrediente>> Get(int IngredienteId)
        {
            try
            {
                var result = _context.Ingrediente.Find(IngredienteId);
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

        [HttpDelete("{IngredienteId}")]
        public async Task<ActionResult> delete(int IngredienteId)
        {            
            try
            {
                //verifica se existe Ingrediente a ser excluído
                var Ingrediente = await _context.Ingrediente.FindAsync(IngredienteId);
                if (Ingrediente == null)
                {
                    //método do EF
                    return NotFound();
                }
                
                _context.Remove(Ingrediente);
                await _context.SaveChangesAsync();
                return NoContent();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            // retorna BadRequest se não conseguiu deletar            
        }
}
}