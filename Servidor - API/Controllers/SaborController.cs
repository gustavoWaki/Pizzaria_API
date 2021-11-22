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
public class SaborController : Controller
{
private readonly PizzariaContext _context;
public SaborController(PizzariaContext context)
{
// construtor
_context = context;
}
[HttpGet]
public ActionResult<List<Sabor>> GetAll() {
return _context.Sabor.ToList();
}

[HttpGet("{SaborId}")]
        public ActionResult<List<Sabor>> Get(int SaborId)
        {
            try
            {
                var result = _context.Sabor.Find(SaborId);
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

    [HttpPost]
        public ActionResult<List<Sabor>> Get(Sabor model)
        {
            try
            {
                var result = _context.Sabor.SingleOrDefault(sabor => sabor.nome == model.nome);
                if(result == null)
                    return BadRequest();
                return Ok(result);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpDelete("{SaborId}")]
        public async Task<ActionResult> delete(int SaborId)
        {            
            try
            {
                //verifica se existe Sabor a ser excluído
                var Sabor = await _context.Sabor.FindAsync(SaborId);
                if (Sabor == null)
                {
                    //método do EF
                    return NotFound();
                }
                
            

                _context.Remove(Sabor);
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