/**
 * 
 */
package com.dwls.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.dwls.bean.BillData;
import com.dwls.bean.DataRef;
import com.dwls.dao.BillDataDAO;
import com.dwls.dao.DataRefDAO;

/**
 * @author lenovo
 *
 */
public class BillFileParser {

	public void readFile(String filePath) throws IOException {
		File file = new File(filePath);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file),"UTF-8"));) {
			String line = "";
			BillDataDAO billDataDAO = new BillDataDAO();
			billDataDAO.deleteAll();
				while ((line = br.readLine()) != null) {
					
					String[] fields = line.split("\\t");
					if(fields != null && fields.length == 115){
						BillData billData = billDataMapper(fields);
						billDataDAO.insert(billData);
					}
				
				}
		}
	}

	public void updateBillFile() {
		BillDataDAO billDataDAO = new BillDataDAO();
		DataRefDAO dataRefDAO = new DataRefDAO();
		DataRef dataRef = null;
		ArrayList<BillData> billDataList = billDataDAO.getAll();
		for(BillData billData: billDataList){
			dataRef= dataRefDAO.getByConsNo(billData.getConsNo1());
			
			if(dataRef != null && billData.getDivName()!="DIV_NAME"){
				billData.setBillMsg5(dataRef.getGroupNo()+" X "+dataRef.getRdNo());
			}
			billDataDAO.update(billData);
		}

	}

	public void writeBillFile(String filePath) throws IOException {
		BillDataDAO billDataDAO = new BillDataDAO();
		ArrayList<BillData> billDataList = billDataDAO.getAll();
		BillData billDataHeader = billDataDAO.getByBillDataHeader();
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filePath),
					"UTF-8"));
			out.write(billDataToStringMapper(billDataHeader)+"\n");
			for (BillData billData : billDataList) {
					out.write(billDataToStringMapper(billData) +"\n");
			}
		}  finally {
			try {
				if (out != null) {
					out.close();
				} else {
					System.out.println("Buffer has not been initialized!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/*public static void main(String[] args) {
		BillFileParser read = new BillFileParser();
		read.readFile("C:\\Users\\lenovo\\Desktop\\bill file\\GPH NonDomestic.csv");
		read.updateBillFile();
		read.writeBillFile("GPH NonDomestic.csv");
	}*/

	public BillData billDataMapper(String[] line) {
		BillData billData = new BillData();
		billData.setDivName(line[0]);
		billData.setTariff(line[1]);
		billData.setLocCode1(line[2]);
		billData.setGroupNo1(line[3]);
		billData.setReaderNo1(line[4]);
		billData.setConsNo1(line[5]);
		billData.setBillMonth1(line[6]);
		billData.setBillNo1(line[7]);
		billData.setBillDate(line[8]);
		billData.setTotUnits1(line[9]);
		billData.setConsName1(line[10]);
		billData.setAddr1(line[11]);
		billData.setNetBill1(line[12]);
		billData.setAddr2(line[13]);
		billData.setAddr3(line[14]);
		billData.setPhone1(line[15]);
		billData.setMobile(line[16]);
		billData.setChqDate1(line[17]);
		billData.setCshDate1(line[18]);
		billData.setPole(line[19]);
		billData.setMeterNo(line[20]);
		billData.setPhase(line[21]);
		billData.setRdgDate(line[22]);
		billData.setConnLoad(line[23]);
		billData.setLoadUnit(line[24]);
		billData.setRdgType(line[25]);
		billData.setContDem(line[26]);
		billData.setMaxDem(line[27]);
		billData.setRdgCurr(line[28]);
		billData.setRdgPre(line[29]);
		billData.setMf(line[30]);
		billData.setPf(line[31]);
		billData.setMtrUnits(line[32]);
		billData.setAssUnits(line[33]);
		billData.setTotUnits2(line[34]);
		billData.setTodUnits(line[35]);
		billData.setLocName(line[36]);
		billData.setLocAddr1(line[37]);
		billData.setEnch(line[38]);
		billData.setLocAddr2(line[39]);
		billData.setFxch(line[40]);
		billData.setLocPhone(line[41]);
		billData.setDuty(line[42]);
		billData.setCess(line[43]);
		billData.setRent(line[44]);
		billData.setOffrName(line[45]);
		billData.setAdjmt(line[46]);
		billData.setOffPhone(line[47]);
		billData.setSdInst(line[48]);
		billData.setCapSch(line[49]);
		billData.setDeName(line[50]);
		billData.setPenalCh(line[51]);
		billData.setDePhone(line[52]);
		billData.setTodSch(line[53]);
		billData.setPurpose(line[54]);
		billData.setSecDep(line[55]);
		billData.setOch(line[56]);
		billData.setBillType(line[57]);
		billData.setSdDue(line[58]);
		billData.setAdvAmt(line[59]);
		billData.setSdInt(line[60]);
		billData.setPrePymtHdr(line[61]);
		billData.setLfRebate(line[62]);
		billData.setCashMsg1(line[63]);
		billData.setLockCr(line[64]);
		billData.setCashMsg2(line[65]);
		billData.setSubsidy(line[66]);
		billData.setCashMsg3(line[67]);
		billData.setMonthBill(line[68]);
		billData.setArrs(line[69]);
		billData.setNetBill2(line[70]);
		billData.setMonth1(line[71]);
		billData.setRdgDate1(line[72]);
		billData.setRdg1(line[73]);
		billData.setSurch(line[74]);
		billData.setMonth2(line[75]);
		billData.setRdgDate2(line[76]);
		billData.setRdg2(line[77]);
		billData.setNetInclSurch1(line[78]);
		billData.setMonth3(line[79]);
		billData.setRdgDate3(line[80]);
		billData.setRdg3(line[81]);
		billData.setMonth4(line[82]);
		billData.setRdgDate4(line[83]);
		billData.setRdg4(line[84]);
		billData.setMonth5(line[85]);
		billData.setRdgDate5(line[86]);
		billData.setRdg5(line[87]);
		billData.setMonth6(line[88]);
		billData.setRdgDate6(line[89]);
		billData.setRdg6(line[90]);
		billData.setAvg1(line[91]);
		billData.setAvg2(line[92]);
		billData.setCirName2(line[93]);
		billData.setBillMsg1(line[94]);
		billData.setBillMsg2(line[95]);
		billData.setBillMsg3(line[96]);
		billData.setBillMsg4(line[97]);
		billData.setBillMsg5(line[98]);
		billData.setScrnMsg(line[99]);
		billData.setBillMonth2(line[100]);
		billData.setBillNo2(line[101]);
		billData.setLocCod2(line[102]);
		billData.setGroupNo2(line[103]);
		billData.setReaderNo2(line[104]);
		billData.setConsNo2(line[105]);
		billData.setConsName2(line[106]);
		billData.setChqDate2(line[107]);
		billData.setCshDate2(line[108]);
		billData.setNetBill3(line[109]);
		billData.setNetInclSurch2(line[110]);
		billData.setOldConsNo(line[111]);
		billData.setSurchargeDemand(line[112]);
		billData.setPrevPendingAmnt(line[113]);
		billData.setMtrReaderName(line[114]);
		return billData;
	}

	public String billDataToStringMapper(BillData billData) {

		String line = "";
		line = billData.getDivName() + "\t" + billData.getTariff() + "\t"
				+ billData.getLocCode1() + "\t" + billData.getGroupNo1() + "\t"
				+ billData.getReaderNo1() + "\t" + billData.getConsNo1() + "\t"
				+ billData.getBillMonth1() + "\t" + billData.getBillNo1()
				+ "\t" + billData.getBillDate() + "\t"
				+ billData.getTotUnits1() + "\t" + billData.getConsName1()
				+ "\t" + billData.getAddr1() + "\t" + billData.getNetBill1()
				+ "\t" + billData.getAddr2() + "\t" + billData.getAddr3()
				+ "\t" + billData.getPhone1() + "\t" + billData.getMobile()
				+ "\t" + billData.getChqDate1() + "\t" + billData.getCshDate1()
				+ "\t" + billData.getPole() + "\t" + billData.getMeterNo()
				+ "\t" + billData.getPhase() + "\t" + billData.getRdgDate()
				+ "\t" + billData.getConnLoad() + "\t" + billData.getLoadUnit()
				+ "\t" + billData.getRdgType() + "\t" + billData.getContDem()
				+ "\t" + billData.getMaxDem() + "\t" + billData.getRdgCurr()
				+ "\t" + billData.getRdgPre() + "\t" + billData.getMf() + "\t"
				+ billData.getPf() + "\t" + billData.getMtrUnits() + "\t"
				+ billData.getAssUnits() + "\t" + billData.getTotUnits2()
				+ "\t" + billData.getTodUnits() + "\t" + billData.getLocName()
				+ "\t" + billData.getLocAddr1() + "\t" + billData.getEnch()
				+ "\t" + billData.getLocAddr2() + "\t" + billData.getFxch()
				+ "\t" + billData.getLocPhone() + "\t" + billData.getDuty()
				+ "\t" + billData.getCess() + "\t" + billData.getRent() + "\t"
				+ billData.getOffrName() + "\t" + billData.getAdjmt() + "\t"
				+ billData.getOffPhone() + "\t" + billData.getSdInst() + "\t"
				+ billData.getCapSch() + "\t" + billData.getDeName() + "\t"
				+ billData.getPenalCh() + "\t" + billData.getDePhone() + "\t"
				+ billData.getTodSch() + "\t" + billData.getPurpose() + "\t"
				+ billData.getSecDep() + "\t" + billData.getOch() + "\t"
				+ billData.getBillType() + "\t" + billData.getSdDue() + "\t"
				+ billData.getAdvAmt() + "\t" + billData.getSdInt() + "\t"
				+ billData.getPrePymtHdr() + "\t" + billData.getLfRebate()
				+ "\t" + billData.getCashMsg1() + "\t" + billData.getLockCr()
				+ "\t" + billData.getCashMsg2() + "\t" + billData.getSubsidy()
				+ "\t" + billData.getCashMsg3() + "\t"
				+ billData.getMonthBill() + "\t" + billData.getArrs() + "\t"
				+ billData.getNetBill2() + "\t" + billData.getMonth1() + "\t"
				+ billData.getRdgDate1() + "\t" + billData.getRdg1() + "\t"
				+ billData.getSurch() + "\t" + billData.getMonth2() + "\t"
				+ billData.getRdgDate2() + "\t" + billData.getRdg2() + "\t"
				+ billData.getNetInclSurch1() + "\t" + billData.getMonth3()
				+ "\t" + billData.getRdgDate3() + "\t" + billData.getRdg3()
				+ "\t" + billData.getMonth4() + "\t" + billData.getRdgDate4()
				+ "\t" + billData.getRdg4() + "\t" + billData.getMonth5()
				+ "\t" + billData.getRdgDate5() + "\t" + billData.getRdg5()
				+ "\t" + billData.getMonth6() + "\t" + billData.getRdgDate6()
				+ "\t" + billData.getRdg6() + "\t" + billData.getAvg1() + "\t"
				+ billData.getAvg2() + "\t" + billData.getCirName2() + "\t"
				+ billData.getBillMsg1() + "\t" + billData.getBillMsg2() + "\t"
				+ billData.getBillMsg3() + "\t" + billData.getBillMsg4() + "\t"
				+ billData.getBillMsg5() + "\t" + billData.getScrnMsg() + "\t"
				+ billData.getBillMonth2() + "\t" + billData.getBillNo2()
				+ "\t" + billData.getLocCod2() + "\t" + billData.getGroupNo2()
				+ "\t" + billData.getReaderNo2() + "\t" + billData.getConsNo2()
				+ "\t" + billData.getConsName2() + "\t"
				+ billData.getChqDate2() + "\t" + billData.getCshDate2() + "\t"
				+ billData.getNetBill3() + "\t" + billData.getNetInclSurch2()
				+ "\t" + billData.getOldConsNo() + "\t"
				+ billData.getSurchargeDemand() + "\t"
				+ billData.getPrevPendingAmnt() + "\t"
				+ billData.getMtrReaderName();
		return line;
	}
}
