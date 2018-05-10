using BudgetBackend.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace BudgetBackend.Controllers
{
    public class ValuesController : ApiController
    {
        BudgetContext db = new BudgetContext();
        // GET api/values
        public IEnumerable<string> Get()
        {
            User user = db.Users.Find(1);
            return new string[] { "Добро пожаловать", "Ответ с сервера!", user.Login , user.Password };
        }

        // GET api/values/5
        public string Get(int id)
        {
            return "value";
        }

        // POST api/values
        [HttpPost]
        public string Post([FromBody]string value)
        {
            String result = value;
            return result + "обратка";

        }

        // PUT api/values/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        public void Delete(int id)
        {
        }
    }
}
