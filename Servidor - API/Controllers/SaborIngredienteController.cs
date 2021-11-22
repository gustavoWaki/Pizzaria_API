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
public class SaborIngredienteController : Controller
{
private readonly PizzariaContext _context;
public SaborIngredienteController(PizzariaContext context)
{
// construtor
_context = context;
}
[HttpGet]
public ActionResult<List<SaborIngrediente>> GetAll() {
return _context.SaborIngrediente.ToList();
}

[HttpGet("{SaborIngredienteId}")]
        public ActionResult<List<SaborIngrediente>> Get(int SaborIngredienteId)
        {
            try
            {
                var result = _context.SaborIngrediente.Find(SaborIngredienteId);
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

        [HttpDelete("{SaborIngredienteId}")]
        public async Task<ActionResult> delete(int SaborIngredienteId)
        {            
            try
            {
                //verifica se existe SaborIngrediente a ser excluído
                var SaborIngrediente = await _context.SaborIngrediente.FindAsync(SaborIngredienteId);
                if (SaborIngrediente == null)
                {
                    //método do EF
                    return NotFound();
                }
                
                _context.Remove(SaborIngrediente);
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