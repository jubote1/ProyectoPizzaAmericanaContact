package pixelpos;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;
import capaModelo.DetallePedidoPixel;


public class Main {
	
	public static ArrayList<PosDetailPixel> listPosdetail = new ArrayList<PosDetailPixel>();
	
	public static String PriceLetter(int a){
		String Prices[]={"PRICEMODE","PRICEA","PRICEB","PRICEC","PRICED","PRICEE","PRICEF","PRICEG","PRICEH","PRICEI","PRICEJ"};
		String Letter = null;
		
		if(a >=0 && a < Prices.length){
			Letter = Prices[a];
		}else{
			Letter = Prices[1];
		}
		return Letter;
	}
	
	public static void PosDetail(Connection con,ArrayList<DetallePedidoPixel> detallePedido,int idEmployee,int NumFactura,java.sql.Date OpenDate){
		
		try {
			double Tax1 = 0, Quan=0, CostEach=0, OrigCostEach=0, NetCostEach=0;
			int i=0,j=0,l=0;
			int ApplyTax1 = 0,idPosDetail = 0 ,prodnum=0,recpos = 0,MasterItem = 0,QuestionId = -1, ProdType=0, StoreNum=0;
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			PreparedStatement Tax = null,AdPosDetail = null,StorenumAndPrice = null,NetandOrigCost = null;
			CallableStatement AutoInc = null;
			ResultSet rsTax = null,rsStorenumAndPrice = null,rsNetandOrigCost = null;
			PosDetailPixel temporal = null;
			String Question[] = {"QUESTION1","QUESTION2","QUESTION3","QUESTION4","QUESTION5"};
			String SqlPrice = "SELECT "+PriceLetter(0)+" FROM dba.Product WHERE PRODNUM = ?";
			String SqlStorenumAndPrice = "SELECT  a.pricemode, a.OPTIONINDEX FROM DBA.forcedchoices a , dba.questions b , dba.product c "
					+ "where a.optionindex = b.optionindex and a.choice = c.prodnum and a.optionindex = (SELECT "+Question[0]+" FROM dba.Product "
					+ "WHERE ISACTIVE = 1 AND "+Question[0]+" > 0 AND PRODNUM = ? ) AND c.PRODNUM = ? AND a.IsActive = 1 order by a.sequence asc";
			String SqlPosDetail = "INSERT INTO DBA.POSDETAIL(UNIQUEID,TRANSACT,PRODNUM,WHOORDER,WHOAUTH,"
					+"COSTEACH,QUAN,TIMEORD,PRINTLOC,SEATNUM,Minutes,NOTAX,HOWORDERED,STATUS,NEXTPOS,"
					+"PRIORPOS,RECPOS,PRODTYPE,ApplyTax1,Applytax2,Applytax3,Applytax4,Applytax5,"
					+"ReduceInventory,StoreNum,STATNUM,RecipeCostEach,OpenDate,"
					+"MealTime,REVCENTER,MasterItem,QuestionId,OrigCostEach,NetCostEach,UpdateStatus) "
					+"VALUES (?,?,?,?,?,"
					+"?,?,?,1,0,0,0,16,5000000,0,"
					+"0,?,?,?,0,0,0,0,"
					+"1,?,1,0,?,"
					+"1,999,?,?,?,?,1)";
			
			for(DetallePedidoPixel cadaDetallePedido:detallePedido){
				ts = new Timestamp(System.currentTimeMillis());
				prodnum = cadaDetallePedido.getIdproductoext();
				Quan = cadaDetallePedido.getCantidad();
				
				if(prodnum != 0){
					//Obteniendo idPosDetail
					AutoInc = con.prepareCall("{call DBA.GetNextAutoInc(?,?)}");
					AutoInc.registerOutParameter(1, Types.INTEGER);
					AutoInc.setString(2, "GETNEXT_POSDETAIL");
					AutoInc.execute();
					idPosDetail = AutoInc.getInt(1);
					AutoInc.close();
					
					//Impuesto
					Tax = con.prepareStatement("SELECT USEITEMCAT FROM dba.Product WHERE PRODNUM = ?");
					Tax.setInt(1, prodnum);
					rsTax = Tax.executeQuery();
					rsTax.next();
					ApplyTax1 = rsTax.getInt(1);
					Tax.close();
					rsTax.close();
				}
				
				if(recpos == 0){
					MasterItem = idPosDetail;
				}else if (prodnum == 0) {
					MasterItem = 0;
				}else if (MasterItem == 0 && recpos != 0) {
					MasterItem = idPosDetail;
				}
				
				if (idPosDetail == MasterItem) {
					QuestionId = 0;
				}else if (prodnum == 2002) {
					QuestionId = MasterItem;
				}
							
				listPosdetail.add(new PosDetailPixel(idPosDetail,NumFactura,prodnum,idEmployee,idEmployee,CostEach,
						Quan,ts,recpos,ProdType,ApplyTax1,StoreNum,OpenDate,MasterItem,QuestionId,OrigCostEach,NetCostEach));
				
				if(prodnum != 0){
					recpos++;				
				}
				
				QuestionId = -1;
				
			}
			
			for (i=0 ; i < listPosdetail.size() ;i++) {
				if (listPosdetail.get(i).getIdproductoext() != 0 & listPosdetail.get(i).getIdproductoext() != 2002) {
					System.out.println("Yo "+listPosdetail.get(i).getIdproductoext()+" y haré la pregunta");
					
					for (j = i+1; j < listPosdetail.size() ;j++) {
						if(listPosdetail.get(j).getIdproductoext() == 0) {
							j=listPosdetail.size();
						}else if (listPosdetail.get(j).getIdproductoext() != 2002) {
							System.out.print("Yo soy "+listPosdetail.get(i).getIdproductoext());
							System.out.print(" Trabajare con: "+listPosdetail.get(j).getIdproductoext());
							for (l = 0; l < Question.length; l++) {
								SqlStorenumAndPrice = "SELECT  a.pricemode, a.OPTIONINDEX FROM DBA.forcedchoices a , dba.questions b , dba.product c "
										+ "where a.optionindex = b.optionindex and a.choice = c.prodnum and a.optionindex = (SELECT "+Question[l]+" FROM dba.Product "
										+ "WHERE ISACTIVE = 1 AND "+Question[l]+" > 0 AND PRODNUM = ? ) AND c.PRODNUM = ? AND a.IsActive = 1 order by a.sequence asc";
								StorenumAndPrice = con.prepareStatement(SqlStorenumAndPrice);
								StorenumAndPrice.setInt(1, listPosdetail.get(i).getIdproductoext());
								StorenumAndPrice.setInt(2, listPosdetail.get(j).getIdproductoext());
								rsStorenumAndPrice = StorenumAndPrice.executeQuery();
								
								while (rsStorenumAndPrice.next()) {
									SqlPrice = "SELECT "+PriceLetter(rsStorenumAndPrice.getInt(1))+" FROM dba.Product WHERE PRODNUM = ?";
									NetandOrigCost = con.prepareStatement(SqlPrice);
									NetandOrigCost.setInt(1, listPosdetail.get(j).getIdproductoext());
									rsNetandOrigCost = NetandOrigCost.executeQuery();
									rsNetandOrigCost.next();
									NetCostEach = rsNetandOrigCost.getDouble(1);
									OrigCostEach = NetCostEach;
									NetandOrigCost.close();
									rsNetandOrigCost.close();
									
									temporal = listPosdetail.get(j);
									temporal.setStorenum(rsStorenumAndPrice.getInt(2));
									temporal.setNetcosteach(NetCostEach);
									temporal.setOrigcostech(OrigCostEach);
									listPosdetail.set(j, temporal);
									
									NetCostEach = 0;
									OrigCostEach = 0;
									
									System.out.print(" y cobraré "+" PriceMode: "+rsStorenumAndPrice.getInt(1)+" StoreNum: "+rsStorenumAndPrice.getInt(2));
								}
								StorenumAndPrice.close();
								rsStorenumAndPrice.close();
							}
							System.out.println();
						}
					}
					if (listPosdetail.get(i).getStorenum() == 0 ) {
						SqlPrice = "SELECT PRICEA FROM dba.Product WHERE PRODNUM = ?";
						NetandOrigCost = con.prepareStatement(SqlPrice);
						NetandOrigCost.setInt(1, listPosdetail.get(i).getIdproductoext());
						rsNetandOrigCost = NetandOrigCost.executeQuery();
						rsNetandOrigCost.next();
						NetCostEach = rsNetandOrigCost.getDouble(1);
						OrigCostEach = NetCostEach;
						NetandOrigCost.close();
						rsNetandOrigCost.close();
						
						temporal = listPosdetail.get(i);
						temporal.setNetcosteach(NetCostEach);
						temporal.setOrigcostech(OrigCostEach);
						listPosdetail.set(i, temporal);
						
						NetCostEach = 0;
						OrigCostEach = 0;
					}
					System.out.println("===============");	
				}
			}
			
			for (i=0 ; i < listPosdetail.size() ;i++) {
				if(listPosdetail.get(i).getIdproductoext() != 0 & listPosdetail.get(i).getIdproductoext() != 2002){
					if(listPosdetail.get(i).getApplytax1() == 1){
						Tax1 = 0.08;
					}else{
						Tax1 = 0;
					}
					CostEach = listPosdetail.get(i).getOrigcostech()/(1+Tax1);
					temporal = listPosdetail.get(i);
					temporal.setCosteach(CostEach);
					listPosdetail.set(i, temporal);
					
					CostEach = 0;
				}
			}
			
			for(PosDetailPixel RunList: listPosdetail){
				if (RunList.getIdproductoext() != 0) {
					//Insertando en tabla PosDetail
					AdPosDetail = con.prepareStatement(SqlPosDetail);
					AdPosDetail.setInt(1, RunList.getIdposdetail());//UNIQUEID
					AdPosDetail.setInt(2, RunList.getNumfactura());//TRANSACT
					AdPosDetail.setInt(3, RunList.getIdproductoext());//PRODNUM
					AdPosDetail.setInt(4, RunList.getWhooder());//WHOORDER
					AdPosDetail.setInt(5, RunList.getWhoauth());//WHOAUTH
					AdPosDetail.setDouble(6, RunList.getCosteach());//COSTEACH
					AdPosDetail.setDouble(7, RunList.getCantidad());//QUAN
					AdPosDetail.setTimestamp(8, RunList.getTimeord());//TIMEORD
					AdPosDetail.setInt(9, RunList.getRecpos());//RECPOS
					AdPosDetail.setInt(10, RunList.getProdtype());//PRODTYPE
					AdPosDetail.setInt(11, RunList.getApplytax1());//ApplyTax1
					AdPosDetail.setInt(12, RunList.getStorenum());//StoreNum
					AdPosDetail.setDate(13, RunList.getOpendate());//OpenDate
					AdPosDetail.setInt(14, RunList.getMasteritem());//MasterItem
					AdPosDetail.setInt(15, RunList.getQuestionid());//QuestionId
					AdPosDetail.setDouble(16, RunList.getOrigcostech());//OrigCostEach
					AdPosDetail.setDouble(17, RunList.getNetcosteach());//NetCostEach
					
					AdPosDetail.execute();
					AdPosDetail.close();
				}
			}
									
		} catch (SQLException e) {
			e.getMessage();
		}	
	}
	
	public static void Recipe(){
		/*String RecetaSQL = "SELECT a.UNIQUEID,a.PRODNUM,b.DESCRIPT,c.INVENNUM,c.DESCRIPT,a.USAGE,c.UNITDES "+
				  "FROM DBA.RECIPE a ,dba.Product b , dba.inventory c "+
				  "WHERE  a.PRODNUM = b.PRODNUM "+
				  "AND  a.INVENNUM = c.INVENNUM "+
				  "AND a.IsActive = 1 "+
				  "AND a.PRODNUM = ? "+
				  "ORDER BY a.INVENNUM asc";

		PreparedStatement Receta = con.prepareStatement(RecetaSQL);
		Receta.setInt(1, 2337);
		ResultSet rs = Receta.executeQuery();
		System.out.println(rs.next());
		while(rs.next()){
		System.out.print(rs.getInt(1)+" ");
		System.out.print(rs.getInt(2)+" ");
		System.out.print(rs.getString(3)+" ");
		System.out.print(rs.getInt(4)+" ");
		System.out.print(rs.getString(5)+" ");
		System.out.print(rs.getDouble(6)+" ");
		System.out.print(rs.getString(7));
		System.out.println();
		}
		rs.close();*/

	}
	
	
	
	public static void InsertarPedidoPos(ArrayList <DetallePedidoPixel> pruebaPedido)
	{
				
		 try
		 {
			 //Class.forName("sybase.jdbc.sqlanywhere.IDriver");
			 //Connection con = DriverManager.getConnection("jdbc:sqlanywhere:dsn=PixelPC;uid=admin;pwd=xxx");//local
			 DriverManager.registerDriver( (Driver)
					 Class.forName( "sybase.jdbc.sqlanywhere.IDriver" ).newInstance() );
			 Connection con = DriverManager.getConnection("jdbc:sqlanywhere:dsn=PixelSqlbase;uid=admin;pwd=xxx");//SystemPos
			 
			 //Obteniendo el dia de apertura
			 Statement state = con.createStatement();
			 String consulta = "select DBA.PixOpenDate() as Opendate";
			 ResultSet rs = state.executeQuery(consulta);
			 rs.next();
			 java.sql.Date DiaApertura = rs.getDate("OpenDate");
			 rs.close();
			 System.out.println("Día De Apertura: "+DiaApertura);
			 
			//Obteniendo PunchIndex
			 PreparedStatement PunchIndex = con.prepareStatement("SELECT a.PunchIndex FROM DBA.PUNCHCLOCK a WHERE  a.EmpNUM = 2007 AND a.OPENDATE = ?");
			 PunchIndex.setDate(1, DiaApertura);
			 rs = PunchIndex.executeQuery();
			 rs.next();
			 int intPunchIndex =rs.getInt(1);
			 System.out.println("intPunchIndex: "+intPunchIndex);
			 PunchIndex.close();
			 rs.close();
			 
			 //Obteniendo el numero de factura
			 CallableStatement proc = con.prepareCall("{call DBA.GetNextAutoInc(?,?)}");
			 proc.registerOutParameter(1, Types.INTEGER);
			 proc.setString(2, "GETNEXT_POSHEADER");
			 proc.execute();
			 int NumFactura = proc.getInt(1);
			 proc.close();
			 System.out.println("NumFactura: "+NumFactura);
			 
			 
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			 Date parsedDate = dateFormat.parse("1899-12-30 00:00:00.000");
			 Timestamp Inicialts = new java.sql.Timestamp(parsedDate.getTime());
			 System.out.println("Fecha Inicial Sistema: "+Inicialts);
			 
			 Timestamp ts = new Timestamp(System.currentTimeMillis());
			 System.out.println("Timestamp: "+ts);

			 
			 //Llenado de ArrayList Pedido de prueba
			 /*ArrayList<DetallePedidoPixel> pruebaPedido = new ArrayList<DetallePedidoPixel>();
			 pruebaPedido.add(new DetallePedidoPixel(2341,1));
			 pruebaPedido.add(new DetallePedidoPixel(2002,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2024,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2229,1));
			 pruebaPedido.add(new DetallePedidoPixel(2273,1));
			 pruebaPedido.add(new DetallePedidoPixel(2130,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2126,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2002,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2022,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2401,1));
			 pruebaPedido.add(new DetallePedidoPixel(2249,1));
			 pruebaPedido.add(new DetallePedidoPixel(2177,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2002,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2110,1));
			 pruebaPedido.add(new DetallePedidoPixel(2421,1));
			 pruebaPedido.add(new DetallePedidoPixel(0,0));
			 pruebaPedido.add(new DetallePedidoPixel(2372,1));
			 pruebaPedido.add(new DetallePedidoPixel(2374,2));
			 pruebaPedido.add(new DetallePedidoPixel(0,0));
			 pruebaPedido.add(new DetallePedidoPixel(2107,1));
			 pruebaPedido.add(new DetallePedidoPixel(0,0));
			 pruebaPedido.add(new DetallePedidoPixel(2389,1));
			 pruebaPedido.add(new DetallePedidoPixel(2430,1));
			 pruebaPedido.add(new DetallePedidoPixel(2116,1));
			 pruebaPedido.add(new DetallePedidoPixel(0,0));
			 pruebaPedido.add(new DetallePedidoPixel(2003,1));
			 pruebaPedido.add(new DetallePedidoPixel(2002,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2067,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2002,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2061,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2002,0.5));
			 pruebaPedido.add(new DetallePedidoPixel(2116,1));
			 pruebaPedido.add(new DetallePedidoPixel(0,0)); */
			 
			 //Insertando nueva transacción en dba.PosHeader
			 String sqlInsertarPosHeader =  "INSERT INTO DBA.POSHEADER"+
											"(TRANSACT,TABLENUM,TIMESTART,TIMEEND,NUMCUST,"+
											"TAX1,TAX2,TAX3,TAX4,TAX5,"+
											"TAX1ABLE,TAX2ABLE,TAX3ABLE,TAX4ABLE,TAX5ABLE,"+
										    "NETTOTAL,WHOSTART,WHOCLOSE,ISSPLIT,SALETYPEINDEX,"+
										    "EXP,STATNUM,STATUS,FINALTOTAL,"+
										    "PUNCHINDEX,Gratuity,OPENDATE,MemCode,"+
										    "TotalPoints,PointsApplied,UpdateStatus,ISDelivery,ScheduleDate,"+
										    "Tax1Exempt,Tax2Exempt,Tax3Exempt,Tax4Exempt,Tax5Exempt,"+
										    "MEMRATE,MealTime,IsInternet,RevCenter,PunchIdxStart,"+
										    "StatNumStart,SecNum,GratAmount,ShipTo, EnforcedGrat)"+
										    "VALUES(?,?,?,?,?,?,?,?,?,?,"+
										    		"?,?,?,?,?,?,?,?,?,?,"+
										    		"?,?,?,?,?,?,?,?,?,?,"+
										    		"?,?,?,?,?,?,?,?,?,?,"+
										    		"?,?,?,?,?,?,?,?)";
			 								
			 
			 PreparedStatement InsertarPosHeader = con.prepareStatement(sqlInsertarPosHeader);
			 InsertarPosHeader.setInt(1, NumFactura);//TRANSACT
			 InsertarPosHeader.setInt(2, 30001);
			 InsertarPosHeader.setTimestamp(3, ts);
			 InsertarPosHeader.setTimestamp(4, ts);
			 InsertarPosHeader.setInt(5, 1);
			 InsertarPosHeader.setDouble(6, 0);//TAX1
			 InsertarPosHeader.setDouble(7, 0);
			 InsertarPosHeader.setDouble(8, 0);
			 InsertarPosHeader.setDouble(9, 0);
			 InsertarPosHeader.setDouble(10, 0);
			 InsertarPosHeader.setDouble(11, 0);//TAX1ABLE
			 InsertarPosHeader.setDouble(12, 0);
			 InsertarPosHeader.setDouble(13, 0);
			 InsertarPosHeader.setDouble(14, 0);
			 InsertarPosHeader.setDouble(15, 0);
			 InsertarPosHeader.setDouble(16, 0);//NETTOTTAL
			 InsertarPosHeader.setInt(17, 2007);
			 InsertarPosHeader.setInt(18,2007);
			 InsertarPosHeader.setInt(19, 1);
			 InsertarPosHeader.setInt(20, 1005);//SALETYPEINDEX
			 InsertarPosHeader.setInt(21, 1);//EXP
			 InsertarPosHeader.setInt(22, 1);//STATNUM 23
			 InsertarPosHeader.setInt(23, 3);
			 InsertarPosHeader.setDouble(24, 0);//FINALTOTAL 25
			 InsertarPosHeader.setInt(25, 0);//PUNCHINDEX 27
			 InsertarPosHeader.setInt(26, 0);
			 InsertarPosHeader.setDate(27, DiaApertura);// 29
			 InsertarPosHeader.setInt(28, 8160);
			 InsertarPosHeader.setDouble(29, 0);//31
			 InsertarPosHeader.setInt(30, 0);
			 InsertarPosHeader.setInt(31, 1);
			 InsertarPosHeader.setInt(32, 0);
			 InsertarPosHeader.setTimestamp(33, Inicialts);//35
			 InsertarPosHeader.setInt(34, 0);
			 InsertarPosHeader.setInt(35, 0);
			 InsertarPosHeader.setInt(36, 0);
			 InsertarPosHeader.setInt(37, 0);
			 InsertarPosHeader.setInt(38, 0);//40
			 InsertarPosHeader.setDouble(39, 1);
			 InsertarPosHeader.setInt(40, 1);
			 InsertarPosHeader.setInt(41, 0);
			 InsertarPosHeader.setInt(42, 999);
			 InsertarPosHeader.setInt(43, intPunchIndex);//45
			 InsertarPosHeader.setInt(44, 1);
			 InsertarPosHeader.setInt(45, 0);
			 InsertarPosHeader.setDouble(46, 0);
			 InsertarPosHeader.setInt(47, 0);
			 InsertarPosHeader.setInt(48, 0);
			 
			 InsertarPosHeader.execute();
			 InsertarPosHeader.close();
			 
			 //Insertando nueva transacción en dba.Tabinfo
			 ts = new Timestamp(System.currentTimeMillis());
			 System.out.println("Timestamp #2: "+ts);
			 
			 String sqlInsertarTabInfo = "INSERT INTO DBA.TABINFO(TRANSACT,EMPNUM,TIMESTART,TABLENUM,InUse,Course)"+
					 "VALUES (?,?,?,?,?,?)";
			 
			 PreparedStatement InsertarTabInfo = con.prepareStatement(sqlInsertarTabInfo);
			 InsertarTabInfo.setInt(1, NumFactura);
			 InsertarTabInfo.setInt(2, 2007);
			 InsertarTabInfo.setTimestamp(3, ts);
			 InsertarTabInfo.setInt(4, 30001);
			 InsertarTabInfo.setInt(5, 1);
			 InsertarTabInfo.setInt(6, 0);
			 
			 InsertarTabInfo.execute();
			 InsertarTabInfo.close();
			 
			 //Insertando nueva transacción en dba.PosHDelivery
			 ts = new Timestamp(System.currentTimeMillis());
			 System.out.println("Timestamp #3: "+ts);
			 
			 String sqlInsertarPosHDelivery = "INSERT INTO DBA.PosHDelivery(Transact,MemCode,OpenDate,DeliveryStatus,UpdateStatus,SNum)"+
					 "VALUES (?,?,?,?,?,?)";
			 
			 PreparedStatement InsertarPosHDelivery = con.prepareStatement(sqlInsertarPosHDelivery);
			 InsertarPosHDelivery.setInt(1, NumFactura);
			 InsertarPosHDelivery.setInt(2, 8160);
			 InsertarPosHDelivery.setDate(3, DiaApertura);
			 InsertarPosHDelivery.setInt(4, 0);
			 InsertarPosHDelivery.setInt(5, 1);
			 InsertarPosHDelivery.setInt(6, -1);
			 
			 InsertarPosHDelivery.execute();
			 InsertarPosHDelivery.close();
			 
			 //Insertando nueva transacción en DBA.POSDETAIL
			 PosDetail(con,pruebaPedido,2007,NumFactura,DiaApertura);
			 
			//Borrando registro en dba.Tabinfo
			 PreparedStatement BorrarTabInfo = con.prepareStatement("DELETE FROM DBA.TABINFO WHERE TRANSACT = ?");
			 BorrarTabInfo.setInt(1, NumFactura);
			 
			 BorrarTabInfo.execute();
			 
			 //Actualizando registro en dba.Member Nota: Por Mejorar
			 ts = new Timestamp(System.currentTimeMillis());
			 System.out.println("Timestamp #5: "+ts);
			 
			 PreparedStatement RegisInMember = con.prepareStatement("SELECT COUNT(*) FROM dba.PosHDelivery WHERE MemCode = ?");
			 RegisInMember.setInt(1, 8160);
			 rs = RegisInMember.executeQuery();
			 rs.next();
			 int NumberDeliverys= rs.getInt(1);
			 RegisInMember.close();
			 rs.close();
			 
			 RegisInMember = con.prepareStatement("SELECT LastTrans FROM dba.Member WHERE MemCode = ?");
			 RegisInMember.setInt(1, 8160);
			 rs = RegisInMember.executeQuery();
			 rs.next();
			 int LastTrans2= rs.getInt(1);
			 RegisInMember.close();
			 rs.close();
			 
			 RegisInMember = con.prepareStatement("SELECT LastTrans2 FROM dba.Member WHERE MemCode = ?");
			 RegisInMember.setInt(1, 8160);
			 rs = RegisInMember.executeQuery();
			 rs.next();
			 int LastTrans3= rs.getInt(1);
			 RegisInMember.close();
			 rs.close();
			 
			 System.out.println("LastTrans2: "+LastTrans2);
			 System.out.println("LastTrans3: "+LastTrans3);
			 System.out.println("NumberDeliverys: "+NumberDeliverys);
			 
			 RegisInMember = con.prepareStatement("UPDATE dba.Member SET LastTrans=?,LastTrans2=?,LastTrans3=?,NumDeliverys=?,LastOrderDate=? WHERE MemCode =?");
			 RegisInMember.setInt(1, NumFactura);
			 RegisInMember.setInt(2, LastTrans2);
			 RegisInMember.setInt(3, LastTrans3);
			 RegisInMember.setInt(4, NumberDeliverys);
			 RegisInMember.setTimestamp(5, ts);
			 RegisInMember.setInt(6, 8160);
			 RegisInMember.executeQuery();
			 RegisInMember.close();
			 
			 //Insertando nueva transacción en DBA.Howpaid
			 ts = new Timestamp(System.currentTimeMillis());
			 System.out.println("Timestamp #6: "+ts);
			 
			 proc = con.prepareCall("{call DBA.GetNextAutoInc(?,?)}");
			 proc.registerOutParameter(1, Types.INTEGER);
			 proc.setString(2, "GETNEXT_HowPaid");
			 proc.execute();
			 int idHowpaid = proc.getInt(1);
			 proc.close();
			 System.out.println("idHowpaid: "+idHowpaid);
			 
			 
			 String sqlInsertarHowpaid =    "INSERT INTO DBA.Howpaid(HowPaidLink,TRANSDATE,EMPNUM,TENDER,METHODNUM,"+
											"CHANGE,AUTHORIZED,MEMCODE,ExchangeRate,"+
											"TRANSACT,PayType,OPENDATE,PUNCHINDEX,UpdateStatus,"+
											"Settled,Status,Approved,STATNUM,IsPayInOut,"+
											"MealTime,RevCenter,Voided,VoidedLink)"+
											"VALUES(?,?,?,?,?,?,?,?,?,?,"+
								    		"?,?,?,?,?,?,?,?,?,?,"+
											"?,?,?)";
			 
			 PreparedStatement InsertarHowpaid = con.prepareStatement(sqlInsertarHowpaid);
			 InsertarHowpaid.setInt(1, idHowpaid);
			 InsertarHowpaid.setTimestamp(2, ts);
			 InsertarHowpaid.setInt(3, 2007);
			 InsertarHowpaid.setDouble(4, 0);
			 InsertarHowpaid.setInt(5, 1001);
			 InsertarHowpaid.setDouble(6, 0);
			 InsertarHowpaid.setInt(7, 199);
			 InsertarHowpaid.setInt(8, 0);
			 InsertarHowpaid.setDouble(9, 1);
			 InsertarHowpaid.setInt(10, NumFactura);
			 InsertarHowpaid.setInt(11, 114);
			 InsertarHowpaid.setDate(12, DiaApertura);
			 InsertarHowpaid.setInt(13, intPunchIndex);
			 InsertarHowpaid.setInt(14, 1);
			 InsertarHowpaid.setInt(15, 1);
			 InsertarHowpaid.setInt(16, 3);
			 InsertarHowpaid.setInt(17, 1);
			 InsertarHowpaid.setInt(18, 1);
			 InsertarHowpaid.setInt(19, 0);
			 InsertarHowpaid.setInt(20, 1);
			 InsertarHowpaid.setInt(21, 999);
			 InsertarHowpaid.setInt(22, 0);
			 InsertarHowpaid.setInt(23, 0);
			 
			 InsertarHowpaid.execute();
			 InsertarHowpaid.close();
			 
			 ts = new Timestamp(System.currentTimeMillis());
			 System.out.println("Timestamp Final: "+ts);
			 System.out.println("Listo1");
			 
		 }catch(Exception e){
			 System.out.println("Tuvimos un fallo " + e.getMessage());
			 e.printStackTrace();
		 }
	}

}
