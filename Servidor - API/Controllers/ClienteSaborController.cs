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
public class ClienteSaborController : Controller
{
private readonly PizzariaContext _context;
public ClienteSaborController(PizzariaContext context)
{
// construtor
_context = context;
}
[HttpGet]
public ActionResult<List<ClienteSabor>> GetAll() {
return _context.ClienteSabor.ToList();
}

[HttpGet("{ClienteSaborId}")]
        public ActionResult<List<Cliente>> Get(int ClienteSaborId)
        {
            try
            {
                var result = _context.ClienteSabor.Find(ClienteSaborId);
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

        [HttpDelete("{ClienteSaborId}")]
        public async Task<ActionResult> delete(int ClienteSaborId)
        {            
            try
            {
                //verifica se existe ClienteSabor a ser excluído
                var ClienteSabor = await _context.ClienteSabor.FindAsync(ClienteSaborId);
                if (ClienteSabor == null)
                {
                    //método do EF
                    return NotFound();
                }
                
                _context.Remove(ClienteSabor);
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